package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.ISectorService;
import com.mercadolibre.finalProject.service.impl.BatchServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BatchServiceTest {

    private BatchServiceImpl service;
    private BatchRepository bathRepository = mock(BatchRepository.class);
    private IProductService productService = mock(IProductService.class);

    @BeforeEach
    public void setup() {
        service = new BatchServiceImpl(bathRepository, productService);
    }


    @SneakyThrows
    @Test
    public void shouldReturnBathStockSizeCorrectly() {
//
//        var dto = TestUtils.getInboundOrderDTOValid();
//        var listBath = dto.getBatchStock();
//
//        var product = new Product(1L);
//        var bath = BatchMapper.toModel(listBath.get(0),dto.getSection().getCode());
//        when(productService.findById(any())).thenReturn(product);
////        when(sectorService.hasType(dto.getSection().getCode(), product.getType())).thenReturn(true);
//        when(sectorService.isThereSpace(any(),anyLong())).thenReturn(true);
//        when(bathRepository.save(any())).thenReturn(bath);
//        var response =  service.create(listBath,dto.getSection().getCode());
//        assertEquals(response.size(),listBath.size());

    }


}
