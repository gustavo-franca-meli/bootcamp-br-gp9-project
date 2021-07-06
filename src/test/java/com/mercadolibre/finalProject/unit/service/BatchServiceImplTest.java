package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.model.mapper.ProductMapper;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.IRepresentativeService;
import com.mercadolibre.finalProject.service.ISectorService;
import com.mercadolibre.finalProject.service.impl.BatchServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BatchServiceImplTest {

    private BatchServiceImpl service;
    private BatchRepository batchRepository = mock(BatchRepository.class);
    private ISectorService sectorService = mock(ISectorService.class);
    private IProductService productService = mock(IProductService.class);
    private IRepresentativeService representativeService = mock(IRepresentativeService.class);

    @BeforeEach
    public void setup() {
        service = new BatchServiceImpl(batchRepository, sectorService, productService, representativeService);
    }


    @SneakyThrows
    @Test
    public void shouldReturnBathStockSizeCorrectly() {
        var dto = TestUtils.getInboundOrderDTOValid();
        var listBath = dto.getBatchStock();

        var product = TestUtils.getProductResponseDTO();
        var bath = BatchMapper.toModel(listBath.get(0),dto.getSection().getCode());
        when(productService.findById(any())).thenReturn(product);
        when(sectorService.hasType(dto.getSection().getCode(), product.getType())).thenReturn(true);
        when(sectorService.isThereSpace(any(),anyLong())).thenReturn(true);
        when(batchRepository.save(any())).thenReturn(bath);
        var response =  service.create(listBath,dto.getSection().getCode());
        assertEquals(response.size(),listBath.size());
    }

    @Test
    public void shouldGetSectionBatchesByProductId() {
        var productResponseDTO = TestUtils.getProductResponseDTO();
        when(productService.findById(Mockito.anyLong())).thenReturn(productResponseDTO);

        var representativeDTO = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findById(Mockito.anyLong())).thenReturn(representativeDTO);

        var expected = TestUtils.getBatchListValid();
        when(batchRepository.findBatchByWarehouseIdAndProductIdAndMinimumDueDate(Mockito.anyLong(), Mockito.anyLong(), Mockito.any())).thenReturn(expected);

        var request = TestUtils.getSectorBatchRequestDTO();
        var got = service.getSectorBatchesByProductId(request);

        var gotBatch = got.getBatchStock().get(0);
        assertEquals(expected.get(0).getId(), gotBatch.getBatchNumber());
    }

}
