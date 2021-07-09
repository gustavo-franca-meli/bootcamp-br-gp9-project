package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.ProductStockInsufficientException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductStockInsufficientExceptionTest {

    @Test
    public void shouldCreateProductStockInsufficientException() {
        var ex = new ProductStockInsufficientException("Error");

        assertNotNull(ex);
    }
}
