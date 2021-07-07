package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.exceptions.BatchNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Product;
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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BatchServiceImplTest {

    private static final String BATCH_NOT_FOUND_MESSAGE = "Doesn't has valid batches with this product. Id product: ";

    private BatchServiceImpl service;
    private final BatchRepository batchRepository = mock(BatchRepository.class);
    private final ISectorService sectorService = mock(ISectorService.class);
    private final IProductService productService = mock(IProductService.class);
    private final IRepresentativeService representativeService = mock(IRepresentativeService.class);

    @BeforeEach
    public void setup() {
        service = new BatchServiceImpl(batchRepository, sectorService, productService, representativeService);
    }


    @SneakyThrows
    @Test
    public void shouldReturnBathStockSizeCorrectly() {
        var dto = TestUtils.getInboundOrderDTOValid();
        var listBath = dto.getBatchStock();

        var product = ProductMapper.toResponseDTO(new Product(1L)); //new Product(1L);
        var bath = BatchMapper.toModel(listBath.get(0),dto.getSection().getCode(),1L);


        when(productService.findById(any())).thenReturn(product);
        when(sectorService.hasType(dto.getSection().getCode(), product.getType())).thenReturn(true);
        when(sectorService.isThereSpace(Mockito.any(), anyLong())).thenReturn(true);
        when(batchRepository.save(any())).thenReturn(bath);
        var response =  service.create(listBath,dto.getSection().getCode(),1L);
        assertEquals(response.size(),listBath.size());
    }

    @Test
    public void shouldGetSectorBatchesByProductId() {
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

    @Test
    public void shouldGetSectorBatchesByProductIdOrderedByCurrentQuantity() {
        var productResponseDTO = TestUtils.getProductResponseDTO();
        when(productService.findById(Mockito.anyLong())).thenReturn(productResponseDTO);

        var representativeDTO = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findById(Mockito.anyLong())).thenReturn(representativeDTO);

        var expected = TestUtils.getBatchListValid();
        when(batchRepository.findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderBySortField(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any())).thenReturn(expected);

        var request = TestUtils.getSectorBatchRequestDTOWithOrderedC();
        var got = service.getSectorBatchesByProductId(request);
        verify(batchRepository, Mockito.times(1)).findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderBySortField(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any());

        var gotBatch = got.getBatchStock().get(0);
        assertEquals(expected.get(0).getId(), gotBatch.getBatchNumber());
    }

    @Test
    public void shouldGetSectorBatchesByProductIdOrderedByDueDate() {
        var productResponseDTO = TestUtils.getProductResponseDTO();
        when(productService.findById(Mockito.anyLong())).thenReturn(productResponseDTO);

        var representativeDTO = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findById(Mockito.anyLong())).thenReturn(representativeDTO);

        var expected = TestUtils.getBatchListValid();
        when(batchRepository.findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderBySortField(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any())).thenReturn(expected);

        var request = TestUtils.getSectorBatchRequestDTOWithOrderedF();
        var got = service.getSectorBatchesByProductId(request);
        verify(batchRepository, Mockito.times(1)).findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderBySortField(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any());

        var gotBatch = got.getBatchStock().get(0);
        assertEquals(expected.get(0).getId(), gotBatch.getBatchNumber());
    }

    @Test
    public void shouldFailGetSectorBatchesByProductId() {
        var productResponseDTO = TestUtils.getProductResponseDTO();
        when(productService.findById(Mockito.anyLong())).thenReturn(productResponseDTO);

        var representativeDTO = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findById(Mockito.anyLong())).thenReturn(representativeDTO);

        var expected = new ArrayList<Batch>();
        when(batchRepository.findBatchByWarehouseIdAndProductIdAndMinimumDueDate(Mockito.anyLong(), Mockito.anyLong(), Mockito.any())).thenReturn(expected);

        var request = TestUtils.getSectorBatchRequestDTO();

        try {
            service.getSectorBatchesByProductId(request);
        } catch (BatchNotFoundException ex) {
            assertEquals(BATCH_NOT_FOUND_MESSAGE + productResponseDTO.getId(), ex.getMessage());
        }
    }

    @Test
    public void shouldFailGetSectorBatchesByProductIdOrderedBy() {
        var productResponseDTO = TestUtils.getProductResponseDTO();
        when(productService.findById(Mockito.anyLong())).thenReturn(productResponseDTO);

        var representativeDTO = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findById(Mockito.anyLong())).thenReturn(representativeDTO);

        var expected = new ArrayList<Batch>();
        when(batchRepository.findBatchByWarehouseIdAndProductIdAndMinimumDueDate(Mockito.anyLong(), Mockito.anyLong(), Mockito.any())).thenReturn(expected);

        var request = TestUtils.getSectorBatchRequestDTOWithOrderedC();

        try {
            service.getSectorBatchesByProductId(request);
        } catch (BatchNotFoundException ex) {
            assertEquals(BATCH_NOT_FOUND_MESSAGE + productResponseDTO.getId(), ex.getMessage());
        }
    }

}
