package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.model.mapper.ProductMapper;
import com.mercadolibre.finalProject.repository.AccountRepository;
import com.mercadolibre.finalProject.service.impl.AccountServiceImpl;
import com.mercadolibre.finalProject.service.impl.BatchServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceImplTest {

    private AccountServiceImpl service;
    private final AccountRepository repository = mock(AccountRepository.class);

    @BeforeEach
    public void setup() {
        service = new AccountServiceImpl(repository);
    }

    @Test
    public void shouldReturnAccountByUsername() {
        var country = TestUtils.getCountry();
        country.setId(1L);
        var expected = TestUtils.getAccountValid();
        expected.setId(1L);
        expected.setUsername("user");
        expected.setPassword("1234");
        expected.setCountry(country);

        when(repository.findByUsername(anyString())).thenReturn(Optional.of(expected));

        var got = service.getAccountByUsername(anyString());
        assertEquals(expected.getUsername(), got.getUsername());

    }

}
