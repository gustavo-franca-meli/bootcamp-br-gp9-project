package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BadRequestExceptionTest {

    @Test
    public void shouldCreateBadRequestException() {
        var exception = new BadRequestException("error");
        assertNotNull(exception);
    }
}
