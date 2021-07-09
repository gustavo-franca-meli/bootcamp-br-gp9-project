package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotFoundExceptionTest {

    @Test
    public void shouldCreateNotFoundException() {
        var exception = new NotFoundException("error");
        assertNotNull(exception);
    }
}
