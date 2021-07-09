package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.CreateBatchStockException;
import com.mercadolibre.finalProject.exceptions.ListException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ListExceptionTest {

    @Test
    public void shouldCreateListException() {
        ListException listException = new CreateBatchStockException("error", new ArrayList<>());

        assertNotNull(listException);
    }

    @Test
    public void shouldSetListException() {
        ListException listException = new CreateBatchStockException("error", new ArrayList<>());
        listException.setBathExceptions(new ArrayList<>());
        assertNotNull(listException);
    }
}
