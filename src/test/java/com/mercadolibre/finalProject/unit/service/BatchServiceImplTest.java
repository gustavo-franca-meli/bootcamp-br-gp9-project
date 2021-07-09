package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.exceptions.*;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
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
        var dto = TestUtils.getInboundOrderDTOValidForCreate();

        var product = ProductMapper.toResponseDTO(new Product(1L));
        when(productService.findById(any())).thenReturn(product);
        when(batchRepository.findById(any())).thenReturn(Optional.empty());
        when(sectorService.hasType(dto.getSection().getCode(), product.getType())).thenReturn(true);
        when(sectorService.isThereSpace(anyLong())).thenReturn(true);

        var expect = TestUtils.getBatchListValid();

        var request = dto.getBatchStock().stream().map(BatchMapper::toDTO).collect(Collectors.toList());
        when(batchRepository.saveAll(any())).thenReturn(expect);
        var got = service.save(request, dto.getSection().getCode(), 1L);
        assertEquals(expect.size(), got.size());

    }

    @Test
    public void shouldGetSectorBatchesByProductId() {
        var productResponseDTO = TestUtils.getProductResponseDTO();
        when(productService.findById(Mockito.anyLong())).thenReturn(productResponseDTO);

        var representativeDTO = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findByAccountUsername(Mockito.anyString())).thenReturn(representativeDTO);

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
        when(representativeService.findByAccountUsername(Mockito.anyString())).thenReturn(representativeDTO);

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
        when(representativeService.findByAccountUsername(Mockito.anyString())).thenReturn(representativeDTO);

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
        when(representativeService.findByAccountUsername(Mockito.anyString())).thenReturn(representativeDTO);

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
        when(representativeService.findByAccountUsername(Mockito.anyString())).thenReturn(representativeDTO);

        var expected = new ArrayList<Batch>();
        when(batchRepository.findBatchByWarehouseIdAndProductIdAndMinimumDueDate(Mockito.anyLong(), Mockito.anyLong(), Mockito.any())).thenReturn(expected);

        var request = TestUtils.getSectorBatchRequestDTOWithOrderedC();

        try {
            service.getSectorBatchesByProductId(request);
        } catch (BatchNotFoundException ex) {
            assertEquals(BATCH_NOT_FOUND_MESSAGE + productResponseDTO.getId(), ex.getMessage());
        }
    }

    @Test
    public void shouldGetBatchesBySectorId() {
        var sectorId = 1L;
        var daysQuantity = 30;
        var batchList = TestUtils.getBatchListValid();

        when(sectorService.exist(sectorId)).thenReturn(true);
        when(batchRepository.findBatchesBySectorId(sectorId, LocalDate.now().plusDays(daysQuantity))).thenReturn(batchList);

        var listExpected = service.getBatchesBySectorId(sectorId, daysQuantity);

        verify(sectorService, Mockito.times(1)).exist(Mockito.any());
        verify(batchRepository, Mockito.times(1)).findBatchesBySectorId(Mockito.anyLong(), Mockito.any());

        assertEquals(batchList.get(0).getId(), listExpected.get(0).getBatchNumber());
        assertEquals(batchList.get(0).getProduct().getId(), listExpected.get(0).getProductId());
        assertEquals(batchList.get(0).getProduct().getProductType(), listExpected.get(0).getProductTypeId());
        assertEquals(batchList.get(0).getDueDate(), listExpected.get(0).getDueDate());
        assertEquals(batchList.get(0).getCurrentQuantity(), listExpected.get(0).getQuantity());
    }

    @Test
    public void shouldThrowSectorNotFoundExceptionFromGetBatchesBySectorId() {
        var sectorId = 1L;
        var daysQuantity = 30;

        when(sectorService.exist(sectorId)).thenReturn(false);

        assertThrows(SectorNotFoundException.class, () -> service.getBatchesBySectorId(sectorId, daysQuantity));

        verify(sectorService, Mockito.times(1)).exist(Mockito.any());
    }

    @Test
    public void shouldThrowNotFoundExceptionFromGetBatchesBySectorId() {
        var sectorId = 1L;
        var daysQuantity = 30;

        when(sectorService.exist(sectorId)).thenReturn(true);
        when(batchRepository.findBatchesBySectorId(sectorId, LocalDate.now().plusDays(daysQuantity))).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> service.getBatchesBySectorId(sectorId, daysQuantity));

        verify(sectorService, Mockito.times(1)).exist(Mockito.any());
        verify(batchRepository, Mockito.times(1)).findBatchesBySectorId(Mockito.anyLong(), Mockito.any());
    }

    @Test
    public void shouldGetBatchesByProductType() {
        var daysQuantity = 30;
        var category = "FF";
        var direction = "asc";
        var listExpected = TestUtils.getBatchListValid();

        when(batchRepository.findBatchesByProductType(anyInt(), any(), any())).thenReturn(listExpected);

        var responseList = service.getBatchesByProductType(daysQuantity, category, direction);

        verify(batchRepository, Mockito.times(1)).findBatchesByProductType(anyInt(), any(), any());

        assertEquals(listExpected.get(0).getId(), responseList.get(0).getBatchNumber());
        assertEquals(listExpected.get(0).getProduct().getId(), responseList.get(0).getProductId());
        assertEquals(listExpected.get(0).getProduct().getProductType(), responseList.get(0).getProductTypeId());
        assertEquals(listExpected.get(0).getDueDate(), responseList.get(0).getDueDate());
        assertEquals(listExpected.get(0).getCurrentQuantity(), responseList.get(0).getQuantity());
    }

    @Test
    public void shouldGetBatchesByProductTypeWithoutDirection() {
        var daysQuantity = 30;
        var category = "FF";
        var listExpected = TestUtils.getBatchListValid();

        when(batchRepository.findBatchesByProductType(anyInt(), any(), any())).thenReturn(listExpected);

        var responseList = service.getBatchesByProductType(daysQuantity, category,null);

        verify(batchRepository, Mockito.times(1)).findBatchesByProductType(anyInt(), any(), any());

        assertEquals(listExpected.get(0).getId(), responseList.get(0).getBatchNumber());
        assertEquals(listExpected.get(0).getProduct().getId(), responseList.get(0).getProductId());
        assertEquals(listExpected.get(0).getProduct().getProductType(), responseList.get(0).getProductTypeId());
        assertEquals(listExpected.get(0).getDueDate(), responseList.get(0).getDueDate());
        assertEquals(listExpected.get(0).getCurrentQuantity(), responseList.get(0).getQuantity());
    }

    @Test
    public void shouldThrowBadRequestExceptionFromGetBatchesByProductType() {
        var daysQuantity = 30;
        var direction = "asc";

        assertThrows(BadRequestException.class, () -> service.getBatchesByProductType(daysQuantity, null, direction));
    }

    @Test
    public void shouldThrowNotFoundRequestExceptionFromGetBatchesByProductType() {
        var daysQuantity = 30;
        var category = "FF";
        var direction = "asc";

        when(batchRepository.findBatchesByProductType(anyInt(), any(), any())).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> service.getBatchesByProductType(daysQuantity, category, direction));

        verify(batchRepository, Mockito.times(1)).findBatchesByProductType(anyInt(), any(), any());
    }
}
