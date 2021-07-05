package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.repository.WarehouseRepository;
import com.mercadolibre.finalProject.service.impl.WarehouseServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class WarehouseServiceImplTest {

    WarehouseRepository repository = Mockito.mock(WarehouseRepository.class);
    WarehouseServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new WarehouseServiceImpl(repository);
    }

    @Test
    void shouldFindWarehouse() {
        var expected = TestUtils.getOptionalWarehouseValid();
        when(repository.findById(1l)).thenReturn(expected);

        var got = service.findById(1l);
        assertEquals(expected.get().getId(), got.getId());
    }

    @Test
    void shouldFailFindWarehouse() {
        var expected = TestUtils.getOptionalWarehouseInvalid();
        when(repository.findById(2l)).thenReturn(expected);
        try {
            service.findById(2l);
        } catch (WarehouseNotFoundException ex) {
            assertEquals(ex.getMessage(),
                    "Warehouse Not Found");
        }

    }
}
