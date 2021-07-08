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
    public void shouldCreateBatchStockExceptionAndGetBatches() {
        var ex = new CreateBatchStockException("Error", new ArrayList<>());
        var list = ex.getBathExceptions();
        assertNotNull(list);
    }

    @Test
    public void shouldCreateBatchStockExceptionAndSetBatches() {
        var ex = new CreateBatchStockException("Error", new ArrayList<>());
        ex.setBathExceptions(new ArrayList<>());
        assertNotNull(ex.getBathExceptions());
    }
}
