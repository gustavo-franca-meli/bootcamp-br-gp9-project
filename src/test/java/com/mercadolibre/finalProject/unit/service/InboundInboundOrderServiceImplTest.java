package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.exceptions.*;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.repository.OrderRepository;
import com.mercadolibre.finalProject.service.IBatchService;
import com.mercadolibre.finalProject.service.IRepresentativeService;
import com.mercadolibre.finalProject.service.ISectorService;
import com.mercadolibre.finalProject.service.IWarehouseService;
import com.mercadolibre.finalProject.service.impl.InboundOrderServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InboundInboundOrderServiceImplTest {

    private final IWarehouseService warehouseService = mock(IWarehouseService.class);
    private final ISectorService sectorService = mock(ISectorService.class);
    private final OrderRepository inboundOrderRepository = mock(OrderRepository.class);
    private final IRepresentativeService representativeService = mock(IRepresentativeService.class);
    private final IBatchService bathService = mock(IBatchService.class);
    private InboundOrderServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new InboundOrderServiceImpl(warehouseService, sectorService, representativeService, inboundOrderRepository, bathService);


    }

    // CREATE METHOD

    @SneakyThrows
    @Test
    public void shouldReturnBatchStockSizeCorrectly() {
        var dto = TestUtils.getInboundOrderDTOValidForCreate();

        var warehouseResponseDto = TestUtils.getWarehouseResponseDTOValid();
        when(warehouseService.findById(Mockito.anyLong())).thenReturn(warehouseResponseDto);

        var representativeResponseDTO = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findByIdAndWarehouseId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(representativeResponseDTO);

        var sector = TestUtils.getSectorDTOResponseValid();
        when(sectorService.findById(dto.getSection().getCode())).thenReturn(sector);

        var order = TestUtils.getOrderValid();
        when(inboundOrderRepository.save(any())).thenReturn(order);

        var batchList = TestUtils.getBatchListValid();
        when(bathService.save(dto.getBatchStock().stream().map(BatchMapper::toDTO).collect(Collectors.toList()), sector.getId(), order.getId())).thenReturn(batchList);

        var expected = dto.getBatchStock().size();
        var got = service.create(dto, Mockito.anyLong());
        assertEquals(expected, got.getBatchStock().size());
    }

    @SneakyThrows
    @Test
    public void shouldCallAOrderRepositorySaveWhenCreate() {
        var inboundOrderDTO = TestUtils.getInboundOrderDTOValidForCreate();

        var warehouseResponseDto = TestUtils.getWarehouseResponseDTOValid();
        when(warehouseService.findById(Mockito.anyLong())).thenReturn(warehouseResponseDto);

        var representativeResponseDTO = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findByIdAndWarehouseId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(representativeResponseDTO);

        var sector = TestUtils.getSectorDTOResponseValid();
        when(sectorService.findById(inboundOrderDTO.getSection().getCode())).thenReturn(sector);

        var order = TestUtils.getOrderValid();
        when(inboundOrderRepository.save(any())).thenReturn(order);

        service.create(inboundOrderDTO, Mockito.anyLong());
        verify(inboundOrderRepository, times(1)).save(any());
    }


    @Test
    public void shouldReturnWarehouseNotFoundWhenWarehouseNotExist() {
        var inboundOrderDTO = TestUtils.getInboundOrderDTOValidForCreate();
        when(warehouseService.findById(Mockito.anyLong())).thenThrow(WarehouseNotFoundException.class);
        assertThrows(WarehouseNotFoundException.class, () -> service.create(inboundOrderDTO, Mockito.anyLong()));
    }

    @SneakyThrows
    @Test
    public void shouldReturnRepresentativeNotFoundWhenRepresentativeNotExistInWareHouse() {
        var warehouseResponseDTO = TestUtils.getWarehouseResponseDTOValid();
        when(warehouseService.findById(Mockito.anyLong())).thenReturn(warehouseResponseDTO);
        when(representativeService.findByIdAndWarehouseId(Mockito.anyLong(), Mockito.anyLong())).thenThrow(RepresentativeNotFound.class);
        var inboundOrderDTO = TestUtils.getInboundOrderDTOValidForCreate();
        assertThrows(RepresentativeNotFound.class, () -> service.create(inboundOrderDTO, Mockito.anyLong()));
    }

    @SneakyThrows
    @Test
    public void shouldReturnSectorNotFoundWhenSectorNotExist() {
        var warehouseResponseDTO = TestUtils.getWarehouseResponseDTOValid();
        when(warehouseService.findById(Mockito.anyLong())).thenReturn(warehouseResponseDTO);

        when(sectorService.findById(Mockito.anyLong())).thenThrow(SectorNotFoundException.class);

        var inboundOrderDTO = TestUtils.getInboundOrderDTOValidForCreate();
        assertThrows(SectorNotFoundException.class, () -> service.create(inboundOrderDTO, Mockito.anyLong()));
    }

    @SneakyThrows
    @Test
    public void shouldReturnBatchStockErrorWhenOneOrMoreBatchHasError() {
        var warehouseResponseDTO = TestUtils.getWarehouseResponseDTOValid();
        when(warehouseService.findById(Mockito.anyLong())).thenReturn(warehouseResponseDTO);

        var representativeResponseDTO = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findByIdAndWarehouseId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(representativeResponseDTO);

        var sector = TestUtils.getSectorDTOResponseValid();
        when(sectorService.findById(Mockito.anyLong())).thenReturn(sector);
        when(bathService.save(Mockito.any(), Mockito.any(),any())).thenThrow(CreateBatchStockException.class);
        var order = TestUtils.getOrderValid();
        when(inboundOrderRepository.save(any())).thenReturn(order);
        var inboundOrderDTO = TestUtils.getInboundOrderDTOValidForCreate();
        assertThrows(CreateBatchStockException.class, () -> service.create(inboundOrderDTO, Mockito.anyLong()));
    }


    //Update METHOD

    @SneakyThrows
    @Test
    public void shouldReturnBatchStockSizeCorrectlyWhenUpdate() {
        var dto = TestUtils.getInboundOrderUpdateDTOValidForUpdate();

        var warehouseResponseDto = TestUtils.getWarehouseResponseDTOValid();
        when(warehouseService.findById(Mockito.anyLong())).thenReturn(warehouseResponseDto);

        var representativeResponseDTO = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findByIdAndWarehouseId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(representativeResponseDTO);

        var sector = TestUtils.getSectorDTOResponseValid();
        when(sectorService.findById(dto.getSection().getCode())).thenReturn(sector);

        var order = TestUtils.getOrderValid();
        order.setBatches(dto.getBatchStock().stream().map(BatchMapper::toDTO).map((a)->BatchMapper.toModel(a,dto.getSection().getCode(),dto.getOrderNumber())).collect(Collectors.toList()));
        when(inboundOrderRepository.findById(dto.getOrderNumber())).thenReturn(Optional.of(order));

        var batchList = TestUtils.getBatchListValid();
        when(bathService.save(dto.getBatchStock().stream().map(BatchMapper::toDTO).collect(Collectors.toList()), sector.getId(), order.getId())).thenReturn(batchList);

        var expected = dto.getBatchStock().size();
        var got = service.update(dto, Mockito.anyLong());
        assertEquals(expected, got.getBatchStock().size());
    }
    @SneakyThrows
    @Test
    public void shouldReturnOrderNotFoundInUpdateWhenOrderNotExist() {
        var dto = TestUtils.getInboundOrderUpdateDTOValidForUpdate();

        var warehouseResponseDto = TestUtils.getWarehouseResponseDTOValid();
        when(warehouseService.findById(Mockito.anyLong())).thenReturn(warehouseResponseDto);

        var representativeResponseDTO = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findByIdAndWarehouseId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(representativeResponseDTO);

        var sector = TestUtils.getSectorDTOResponseValid();
        when(sectorService.findById(dto.getSection().getCode())).thenReturn(sector);

        var order = TestUtils.getOrderValid();
        order.setBatches(dto.getBatchStock().stream().map(BatchMapper::toDTO).map((a)->BatchMapper.toModel(a,dto.getSection().getCode(),dto.getOrderNumber())).collect(Collectors.toList()));
        when(inboundOrderRepository.findById(dto.getOrderNumber())).thenReturn(Optional.empty());

        var batchList = TestUtils.getBatchListValid();
        when(bathService.save(dto.getBatchStock().stream().map(BatchMapper::toDTO).collect(Collectors.toList()), sector.getId(), order.getId())).thenReturn(batchList);

        var expected = dto.getBatchStock().size();
        assertThrows(InboundOrderNotFoundException.class,() -> service.update(dto, Mockito.anyLong()));

    }
}

