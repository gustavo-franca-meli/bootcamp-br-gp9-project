package com.mercadolibre.finalProject.unit.model.mapper;

import com.mercadolibre.finalProject.model.mapper.AccountMapper;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountMapperTest {

    @Test
    public void assembleAccountResponseDTOOf() {
        var country = TestUtils.getCountry();
        country.setId(1L);
        var expected = TestUtils.getAccountValid();
        expected.setId(1L);
        expected.setUsername("user");
        expected.setPassword("1234");
        expected.setRol(1);
        expected.setCountry(country);

        var got = AccountMapper.toResponseDTO(expected);

        assertEquals(expected.getId(), got.getId());
        assertEquals(expected.getUsername(), got.getUsername());
    }

}
