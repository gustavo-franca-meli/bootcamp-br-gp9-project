package com.mercadolibre.finalProject.unit.model.mapper;

import com.mercadolibre.finalProject.model.mapper.WarehouseMapper;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarehouseMapperTest {

    @Test
    void shouldAssembleWarehouseResponseDTO() {
        var expected = TestUtils.getWarehouseValid();
        var got = WarehouseMapper.toResponseDTO(expected);

        assertEquals(expected.getId(), got.getId());
        assertEquals(expected.getName(), got.getName());
    }
}
