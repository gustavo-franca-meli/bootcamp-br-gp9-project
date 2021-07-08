package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.BatchCreateException;
import com.mercadolibre.finalProject.exceptions.SubError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SubErrorTest {

    @Test
    public void shouldCreateSubError() {
        SubError error = new BatchCreateException(1L, "Error");
        assertNotNull(error);
    }

}
