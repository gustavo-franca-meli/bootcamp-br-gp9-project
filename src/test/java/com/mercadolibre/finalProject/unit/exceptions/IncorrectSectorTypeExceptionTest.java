package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.IncorrectSectorTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IncorrectSectorTypeExceptionTest {

    @Test
    public void shouldCreateIncorrectSectorTypeException() {
        var ex = new IncorrectSectorTypeException("Error");
        assertNotNull(ex);
    }
}
