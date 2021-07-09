package com.mercadolibre.finalProject.unit.model.mapper;

import com.mercadolibre.finalProject.model.mapper.BatchPurchaseOrderMapper;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BatchPurchaseOrderMapperTest {

    @Test
    public void assembleBatchPurchaseOrderResponseDTO() {
        var expected = TestUtils.getBatchPurchaseOrder();
        var got = BatchPurchaseOrderMapper.toResponseDTO(expected);

        assertEquals(expected.getId(), got.getId());
    }

    @Test
    public void assembleBatchPurchaseOrderDTO() {
        var expected = TestUtils.getBatchDTO();
        var got = BatchPurchaseOrderMapper.toDTO(expected, 10);

        assertEquals(expected.getId(), got.getBatchDTO().getId());
    }

    @Test
    public void assembleBatchPurchaseOrder() {
        var expected = TestUtils.getBatchValid();
        var got = BatchPurchaseOrderMapper.toModel(expected, 10);

        assertEquals(expected.getId(), got.getBatch().getId());
    }

    @Test
    public void assembleBatchPurchaseOrderOverchargeMethod() {
        var productBatchesPurchaseOrder = TestUtils.getProductBatchesPurchaseOrder();
        var expected = TestUtils.getBatchValid();
        var got = BatchPurchaseOrderMapper.toModel(expected, 10, productBatchesPurchaseOrder);

        assertEquals(expected.getId(), got.getBatch().getId());
    }

    @Test
    public void assembleListBatchPurchaseOrderResponseDTO() {
        var b1 = TestUtils.getBatchPurchaseOrder();
        var b2 = TestUtils.getBatchPurchaseOrder();
        var expected = Arrays.asList(b1, b2);
        var got = BatchPurchaseOrderMapper.toListResponseDTO(expected);

        assertEquals(expected.get(0).getId(), got.get(0).getId());
    }

}
