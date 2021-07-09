package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.CreateBatchStockException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateBatchStockExceptionTest {

    @Test
    public void shouldCreateBatchStockException() {
        var ex = new CreateBatchStockException("Error", new ArrayList<>());
        assertNotNull(ex);
    }

    @Test
    public void shouldCreateBatchStockExceptionSetAndGetBatchesException() {
        var ex = new CreateBatchStockException("Error", new ArrayList<>());
        ex.setBatchExceptions(new ArrayList<>());
        assertNotNull(ex.getBatchExceptions());
    }
}
