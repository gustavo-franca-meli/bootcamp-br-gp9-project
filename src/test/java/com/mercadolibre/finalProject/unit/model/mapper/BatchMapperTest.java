package com.mercadolibre.finalProject.unit.model.mapper;

import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BatchMapperTest {

    @Test
    void shouldAssembleModelOfBatchDTO() {
        var expected = TestUtils.getBatchDTOValid();
        var got = BatchMapper.toModel(expected, 1L, 1L);

        assertEquals(expected.getId(), got.getId());
        assertEquals(expected.getProductId(), got.getProduct().getId());
    }

    @Test
    void shouldAssembleBatchDTOOfModel() {
        var expected = TestUtils.getBatchValid();
        var got = BatchMapper.toDTO(expected);

        assertEquals(expected.getId(), got.getId());
        assertEquals(expected.getProduct().getId(), got.getProductId());
    }

    @Test
    void shouldAssembleSectorBatchResponseDTOOfBatchList() {
        var expected = TestUtils.getBatchListValid();
        var got = BatchMapper.toSectorBatchResponseDTO(expected);

        var expectedBatch = expected.get(0);
        var gotExpected = got.getBatchStock().get(0);
        assertEquals(expectedBatch.getId(), gotExpected.getBatchNumber());
    }

    @Test
    void shouldAssembleBatchIdentificationResponseDTO() {
        var got = BatchMapper.assembleBatchIdentificationResponseDTOOf(1L, 1L);

        assertEquals(1L, got.getSectorCode());
        assertEquals(1L, got.getWarehouseCode());
    }

    @Test
    void shouldAssembleListBatchStockResponseDTOOfListBatch() {
        var expected = TestUtils.getBatchListValid();
        var got = BatchMapper.toListBatchStockResponseDTO(expected);

        assertEquals(expected.get(0).getId(), got.get(0).getBatchNumber());
        assertEquals(expected.get(0).getDueDate(), got.get(0).getDueDate());
    }

    @Test
    void shouldAssembleBatchStockResponseDTOOfBatch() {
        var expected = TestUtils.getBatchValid();
        var got = BatchMapper.toBatchStockResponseDTO(expected);
        assertEquals(expected.getId(), got.getBatchNumber());
        assertEquals(expected.getDueDate(), got.getDueDate());
    }

    @Test
    void shouldAssembleBatchValidateDateResponseDTOOfBatch() {
        var expected = TestUtils.getBatchValid();
        var got = BatchMapper.toBatchValidateDateResponseDTO(expected);

        assertEquals(expected.getId(), got.getBatchNumber());
        assertEquals(expected.getProduct().getId(), got.getProductId());
        assertEquals(expected.getProduct().getProductType(), got.getProductTypeId());
        assertEquals(expected.getDueDate(), got.getDueDate());
        assertEquals(expected.getCurrentQuantity(), got.getQuantity());
    }

    @Test
    void shouldAssembleListBatchValidateDateResponseDTOOfListBatch() {
        var expected = TestUtils.getBatchListValid();
        var got = BatchMapper.toListBatchValidateDateResponseDTO(expected);

        assertEquals(expected.get(0).getId(), got.get(0).getBatchNumber());
        assertEquals(expected.get(0).getProduct().getId(), got.get(0).getProductId());
        assertEquals(expected.get(0).getProduct().getProductType(), got.get(0).getProductTypeId());
        assertEquals(expected.get(0).getDueDate(), got.get(0).getDueDate());
        assertEquals(expected.get(0).getCurrentQuantity(), got.get(0).getQuantity());
    }
}
