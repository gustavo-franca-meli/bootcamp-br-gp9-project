package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.exceptions.BathCreateException;
import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.enums.SectorType;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.ISectorService;
import com.mercadolibre.finalProject.service.impl.BatchServiceImpl;
import com.mercadolibre.finalProject.util.faker.InboundOrderFaker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BatchServiceTest {

    private BatchServiceImpl service;
    private ISectorService sectorService = mock(ISectorService.class);
    private BatchRepository bathRepository = mock(BatchRepository.class);
    private IProductService productService = mock(IProductService.class);

    @BeforeEach
    public void setup() {
        service = new BatchServiceImpl(sectorService, bathRepository, productService);
    }


    @SneakyThrows
    @Test
    public void shouldReturnBathStockSizeCorrectly() {

        var dto = InboundOrderFaker.getValidInboundOrderRequest();
        var listBath = dto.getBatchStock();

        var product = new Product(1L);
        var bath = BatchMapper.toModel(listBath.get(0),dto.getSection().getCode());
        when(productService.findById(any())).thenReturn(product);
        when(sectorService.hasType(dto.getSection().getCode(), product.getType())).thenReturn(true);
        when(sectorService.isThereSpace(any())).thenReturn(true);
        when(bathRepository.save(any())).thenReturn(bath);
        var response =  service.create(listBath,dto.getSection().getCode());
        assertEquals(response.size(),listBath.size());

    }


}
