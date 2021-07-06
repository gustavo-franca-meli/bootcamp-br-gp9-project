package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.exceptions.RepresentativeNotFound;
import com.mercadolibre.finalProject.repository.RepresentativeRepository;
import com.mercadolibre.finalProject.service.impl.RepresentativeServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RepresentativeServiceImplTest {

    RepresentativeRepository repository = Mockito.mock(RepresentativeRepository.class);
    RepresentativeServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new RepresentativeServiceImpl(repository);
    }

    @Test
    void shouldFindRepresentativeById() {
        var expected = TestUtils.getRepresentativeValid();
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));

        var got = service.findById(Mockito.anyLong());
        assertEquals(expected.getId(), got.getId());
    }

    @Test
    void shouldFailFindRepresentativeById() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        assertThrows(RepresentativeNotFound.class, () -> service.findById(Mockito.anyLong()));
    }

    @Test
    void shouldFindRepresentativeByIdAndWarehouseId() {
        var expected = TestUtils.getRepresentativeValid();
        when(repository.findByIdAndWarehouseId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(expected));

        var got = service.findByIdAndWarehouseId(Mockito.anyLong(), Mockito.anyLong());
        assertEquals(expected.getId(), got.getId());
    }

    @Test
    void shouldFailFindRepresentativeByIdAndWarehouseId() {
        when(repository.findByIdAndWarehouseId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        assertThrows(RepresentativeNotFound.class, () -> service.findByIdAndWarehouseId(Mockito.anyLong(), Mockito.anyLong()));
    }

}
