package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.exceptions.CreateBathStockException;
import com.mercadolibre.finalProject.exceptions.RepresentativeNotFound;
import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.model.mapper.WarehouseMapper;
import com.mercadolibre.finalProject.repository.OrderRepository;
import com.mercadolibre.finalProject.service.IBatchService;
import com.mercadolibre.finalProject.service.IRepresentativeService;
import com.mercadolibre.finalProject.service.ISectorService;
import com.mercadolibre.finalProject.service.IWarehouseService;
import com.mercadolibre.finalProject.service.impl.InboundOrderServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import com.mercadolibre.finalProject.util.faker.InboundOrderFaker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class InboundOrderServiceImplTest {


    private final IWarehouseService warehouseService = mock(IWarehouseService.class);
    private final ISectorService sectorService = mock(ISectorService.class);
    private final OrderRepository inboundOrderRepository = mock(OrderRepository.class);
    private final IRepresentativeService representativeService = mock(IRepresentativeService.class);
    private final IBatchService bathService = mock(IBatchService.class);
    private final String representativeId = UUID.randomUUID().toString();
    private InboundOrderServiceImpl service;
    private InboundOrderDTO validRequest;
    private Warehouse warehouse;

    @BeforeEach
    public void setup() {
        service = new InboundOrderServiceImpl(warehouseService, sectorService, representativeService, inboundOrderRepository, bathService);
        validRequest = InboundOrderFaker.getValidInboundOrderRequest();
        warehouse = TestUtils.getWarehouseValid();
    }


    @SneakyThrows
    @Test
    public void shouldReturnBathStockSizeCorrectly() {
        var dto = InboundOrderFaker.getValidInboundOrderRequest();
        var representativeId = UUID.randomUUID().toString();
        var expectedSize = dto.getBatchStock().size();

        var warehouse = TestUtils.getWarehouseValid();
        var warehouseResponseDto = WarehouseMapper.toResponseDTO(warehouse);
        var sectorId = warehouse.getSectors().get(0).getId();
        when(warehouseService.findById(dto.getSection().getWarehouseCode())).thenReturn(warehouseResponseDto);

        var representative = TestUtils.getRepresentativeValid();
        when(representativeService.findByIdAndWarehouseId(representativeId, warehouse.getId())).thenReturn(representative);

        var sector = TestUtils.getSectorValid();
        when(sectorService.findById(dto.getSection().getCode())).thenReturn(sector);

        var batchModelList = dto.getBatchStock().stream().map((a) -> BatchMapper.toModel(a, sectorId)).collect(Collectors.toList());
        when(bathService.create(dto.getBatchStock(), sector)).thenReturn(batchModelList);

        var got = service.create(dto, representativeId);
        assertEquals(expectedSize, got.getBatchStock().size());
    }

    @SneakyThrows
    @Test
    public void shouldCallAOrderRepositorySaveWhenCreate() {
        var warehouseResponseDTO = WarehouseMapper.toResponseDTO(warehouse);
        when(warehouseService.findById(validRequest.getSection().getWarehouseCode())).thenReturn(warehouseResponseDTO);

        var order = TestUtils.getOrderValid();
        when(inboundOrderRepository.save(order)).thenReturn(null);
        service.create(validRequest, UUID.randomUUID().toString());
        verify(inboundOrderRepository, times(1)).save(any());
    }


    @SneakyThrows
    @Test
    public void shouldReturnWarehouseNotFoundWhenWarehouseNotExist() {
        when(warehouseService.findById(validRequest.getSection().getWarehouseCode())).thenThrow(WarehouseNotFoundException.class);
        assertThrows(WarehouseNotFoundException.class, () -> service.create(validRequest, UUID.randomUUID().toString()));
    }

    @SneakyThrows
    @Test
    public void shouldReturnRepresentativeNotFoundWhenRepresentativeNotExistInWareHouse() {
        var warehouse = TestUtils.getWarehouseValid();
        var expectedWarehouse = WarehouseMapper.toResponseDTO(warehouse);
        when(warehouseService.findById(validRequest.getSection().getWarehouseCode())).thenReturn(expectedWarehouse);
        when(representativeService.findByIdAndWarehouseId(representativeId, expectedWarehouse.getId())).thenThrow(RepresentativeNotFound.class);
        assertThrows(RepresentativeNotFound.class, () -> service.create(validRequest, representativeId));
    }

    @SneakyThrows
    @Test
    public void shouldReturnSectorNotFoundWhenSectorNotExist() {
        var warehouseResponseDTO = WarehouseMapper.toResponseDTO(warehouse);
        when(warehouseService.findById(validRequest.getSection().getWarehouseCode())).thenReturn(warehouseResponseDTO);

        when(sectorService.findById(validRequest.getSection().getCode())).thenThrow(SectorNotFoundException.class);
        assertThrows(SectorNotFoundException.class, () -> service.create(validRequest, representativeId));
    }

    @SneakyThrows
    @Test
    public void shouldReturnBathStockErrorWhenOneOrMoreBathHasError() {
        var warehouseResponseDTO = WarehouseMapper.toResponseDTO(warehouse);
        when(warehouseService.findById(validRequest.getSection().getWarehouseCode())).thenReturn(warehouseResponseDTO);

        var sector = InboundOrderFaker.getSector(validRequest.getSection().getCode());
        when(sectorService.findById(any())).thenReturn(sector);
        when(bathService.create(validRequest.getBatchStock(), sector)).thenThrow(CreateBathStockException.class);
        assertThrows(CreateBathStockException.class, () -> service.create(validRequest, UUID.randomUUID().toString()));
    }
}

