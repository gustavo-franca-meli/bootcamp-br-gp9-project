package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.StockInsufficientException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StockInsufficientExceptionTest {

    @Test
    public void shouldCreateStockInsufficientException() {
        var ex = new StockInsufficientException("Error", new ArrayList<>());

        assertNotNull(ex);
    }
}
