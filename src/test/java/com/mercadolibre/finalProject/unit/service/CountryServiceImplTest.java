package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.repository.CountryRepository;
import com.mercadolibre.finalProject.service.impl.CountryServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CountryServiceImplTest {

    private CountryServiceImpl service;
    private final CountryRepository repository = mock(CountryRepository.class);
    private final ModelMapper modelMapper = mock(ModelMapper.class);

    @BeforeEach
    public void setup() {
        service = new CountryServiceImpl(repository, modelMapper);
    }

    @Test
    public void shouldCreateCountry() {
        var expected = TestUtils.getCountry();
        when(repository.save(Mockito.any())).thenReturn(expected);

        var request = TestUtils.getCountryRequestDTO();
        var got = service.create(request);

        assertEquals(expected.getName(), got.getName());
    }

    @Test
    public void shouldUpdateCountry() {
        var expected = TestUtils.getCountry();
        expected.setId(1L);

        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
        when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(expected);
        when(repository.save(Mockito.any())).thenReturn(expected);

        var request = TestUtils.getCountryRequestDTO();
        var got = service.update(1L, request);

        assertEquals(expected.getName(), got.getName());
    }

    @Test
    public void shouldDeleteCountry() {
        var expected = TestUtils.getCountry();
        expected.setId(1L);
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));

        service.delete(expected.getId());
        verify(repository, times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    public void shouldFindCountryById() {
        var expected = TestUtils.getCountry();
        expected.setId(1L);

        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));

        var got = service.findById(Mockito.anyLong());
        assertEquals(expected.getName(), got.getName());
    }

    @Test
    public void shouldFindAllCountry() {
        var c1 = TestUtils.getCountry();
        c1.setId(1L);
        var c2 = TestUtils.getCountry();
        c2.setId(1L);

        var expected = Arrays.asList(c1, c2);
        when(repository.findAll()).thenReturn(expected);

        var got = service.findAll();
        assertEquals(expected.get(0).getName(), got.get(0).getName());
    }

    @Test
    public void shouldFindCountryName() {
        var expected = TestUtils.getCountry();
        expected.setId(1L);

        when(repository.findByName(Mockito.anyString())).thenReturn(expected);

        var got = service.findByCountry(Mockito.anyString());
        assertEquals(expected.getName(), got.getName());
    }

}
