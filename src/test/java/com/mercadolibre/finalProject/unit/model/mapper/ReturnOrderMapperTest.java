package com.mercadolibre.finalProject.unit.model.mapper;

import com.mercadolibre.finalProject.model.mapper.ReturnOrderMapper;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReturnOrderMapperTest {

    @Test
    public void shouldAssembleModel() {
        var expected = TestUtils.getReturnOrderRequestDTO();

        var got = ReturnOrderMapper.toModel(expected);

        assertEquals(expected.getPurchaseOrderId(), got.getPurchaseOrder().getId());
    }
}
