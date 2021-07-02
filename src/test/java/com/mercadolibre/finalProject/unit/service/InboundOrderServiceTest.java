package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.SectionDto;
import com.mercadolibre.finalProject.exceptions.CreateBathStockException;
import com.mercadolibre.finalProject.exceptions.RepresentativeNotFound;
import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Representative;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.repository.OrderRepository;
import com.mercadolibre.finalProject.service.IBathService;
import com.mercadolibre.finalProject.service.IRepresentativeService;
import com.mercadolibre.finalProject.service.ISectorService;
import com.mercadolibre.finalProject.service.IWarehouseService;
import com.mercadolibre.finalProject.service.impl.InboundOrderService;
import com.mercadolibre.finalProject.util.faker.BathRequestFaker;
import com.mercadolibre.finalProject.util.faker.InboundOrderFaker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InboundOrderServiceTest {


    private IWarehouseService warehouseService = mock(IWarehouseService.class);
    private ISectorService sectorService =  mock(ISectorService.class);
    private OrderRepository inboundOrderRepository = mock(OrderRepository.class);
    private IRepresentativeService representativeService = mock(IRepresentativeService.class);
    private IBathService bathService = mock(IBathService.class);

    private InboundOrderService service;
    private InboundOrderDTO validRequest;
    private String representativeId = UUID.randomUUID().toString();

    @BeforeEach  public void setup(){
        service = new InboundOrderService(warehouseService,sectorService,representativeService,inboundOrderRepository,bathService);
        validRequest = InboundOrderFaker.getValidInboundOrderRequest();
    }


    @SneakyThrows
    @Test public void shouldReturnBathStockSizeCorrectly(){


        var section = new SectionDto(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        var bathList = new ArrayList<BatchDTO>();

        bathList.add(BathRequestFaker.validRequest());
        bathList.add(BathRequestFaker.validRequest());
        bathList.add(BathRequestFaker.validRequest());
        bathList.add(BathRequestFaker.validRequest());
        bathList.add(BathRequestFaker.validRequest());

        var dto = new InboundOrderDTO(10, LocalDate.now(),section,bathList);
        var representeId = UUID.randomUUID().toString();



        var warehouse = new Warehouse();
        warehouse.setSectors(new ArrayList<>());
        warehouse.setId(UUID.fromString(dto.getSection().getWarehouseCode()));
        var sector = new Sector();
        var bathModelList =  bathList.stream().map((a)-> BatchMapper.toModel(a,sector.getId())).collect(Collectors.toList());
        when(warehouseService.findById(dto.getSection().getWarehouseCode())).thenReturn(warehouse);
        when(representativeService.findByIdAndWarehouse(representeId,warehouse)).thenReturn(new Representative());
        when(sectorService.findById(dto.getSection().getCode())).thenReturn(sector);
        when(bathService.create(dto.getBatchStock(),sector)).thenReturn(bathModelList);
        var response = service.create(dto,representeId);

        assertEquals(response.getBatchStock().size(),bathList.size());

    }

    @SneakyThrows
    @Test public void shouldCallAOrderRepositorySaveWhenCreate(){
        var response = service.create(validRequest,UUID.randomUUID().toString());
        verify(inboundOrderRepository,times(1)).save(any());
    }


    @SneakyThrows
    @Test public void shouldReturnWarehouseNotFoundWhenWarehouseNotExist(){
        when(warehouseService.findById(validRequest.getSection().getWarehouseCode())).thenThrow(WarehouseNotFoundException.class);
        assertThrows(WarehouseNotFoundException.class,()-> service.create(validRequest,UUID.randomUUID().toString()));
    }

    @SneakyThrows
    @Test public void shouldReturnRepresentativeNotFoundWhenRepresentativeNotExistInWareHouse(){
        var warehouse = InboundOrderFaker.getValidwarehouse();
        when(warehouseService.findById(validRequest.getSection().getWarehouseCode())).thenReturn(warehouse);
        when(representativeService.findByIdAndWarehouse(representativeId,warehouse)).thenThrow(RepresentativeNotFound.class);
        assertThrows(RepresentativeNotFound.class,()-> service.create(validRequest,representativeId));
    }
    @SneakyThrows
    @Test public void shouldReturnSectorNotFoundWhenSectorNotExist(){
        when(sectorService.findById(validRequest.getSection().getCode())).thenThrow(SectorNotFoundException.class);
        assertThrows(SectorNotFoundException.class,()-> service.create(validRequest,representativeId));
    }
    @SneakyThrows
    @Test public void shouldReturnBathStockErrorWhenOneOrMoreBathHasError(){
        var sector = InboundOrderFaker.getSector(validRequest.getSection().getCode());
        when(sectorService.findById(any())).thenReturn(sector);
        when(bathService.create(validRequest.getBatchStock(),sector)).thenThrow(CreateBathStockException.class);
        assertThrows(CreateBathStockException.class,()-> service.create(validRequest,UUID.randomUUID().toString()));
    }
}

