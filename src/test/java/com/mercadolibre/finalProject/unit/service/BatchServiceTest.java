package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.model.mapper.ProductMapper;
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
    private ISectorService sectorService = mock(ISectorService.class);
    private IProductService productService = mock(IProductService.class);

    @BeforeEach
    public void setup() {
        service = new BatchServiceImpl(bathRepository, sectorService, productService);
    }


    @SneakyThrows
    @Test
    public void shouldReturnBathStockSizeCorrectly() {

        var dto = TestUtils.getInboundOrderDTOValidForCreate();
        var listBath = dto.getBatchStock();

        var product = ProductMapper.toResponseDTO(new Product(1L)); //new Product(1L);
        var bath = BatchMapper.toModel(listBath.get(0),dto.getSection().getCode(),1L);


        when(productService.findById(any())).thenReturn(product);
        when(sectorService.hasType(dto.getSection().getCode(), product.getProductTypes())).thenReturn(true);
        when(sectorService.isThereSpace(anyLong())).thenReturn(true);
        when(bathRepository.save(any())).thenReturn(bath);
        var response =  service.create(listBath,dto.getSection().getCode(),1L);
        assertEquals(response.size(),listBath.size());

    }


}
