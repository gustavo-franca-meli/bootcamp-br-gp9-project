package com.mercadolibre.finalProject.unit.model.mapper;

import com.mercadolibre.finalProject.model.mapper.ProductMapper;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {

    @Test
    void shouldAssembleProductResponseDTO() {
        var expected = TestUtils.getProductValid();
        var got = ProductMapper.toResponseDTO(expected);

        assertEquals(expected.getId(), got.getId());
    }

    @Test
    void shouldAssembleListProductResponseDTO() {
        var expected = Arrays.asList(TestUtils.getProductValid(), TestUtils.getProductValid());
        var got = ProductMapper.toListResponseDTO(expected);

        assertEquals(expected.get(0).getId(), got.get(0).getId());
    }
}
