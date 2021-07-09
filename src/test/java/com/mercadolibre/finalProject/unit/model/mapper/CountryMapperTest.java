package com.mercadolibre.finalProject.unit.model.mapper;

import com.mercadolibre.finalProject.model.mapper.CountryMapper;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryMapperTest {

    @Test
    void shouldAssembleListCountryResponseDTO() {
        var c1 = TestUtils.getCountry();
        c1.setId(1L);

        var c2 = TestUtils.getCountry();
        c2.setId(2L);

        var got = CountryMapper.listToResponseDTO(Arrays.asList(c1, c2));

        assertEquals(c1.getId(), got.get(0).getId());
    }

    @Test
    void shouldAssembleCountryResponseDTO() {
        var expected = TestUtils.getCountry();
        expected.setId(1L);
        var got = CountryMapper.toResponseDTO(expected);

        assertEquals(expected.getId(), got.getId());
    }

}
