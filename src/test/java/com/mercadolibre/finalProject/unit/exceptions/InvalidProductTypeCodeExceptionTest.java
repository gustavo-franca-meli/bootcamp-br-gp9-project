package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.InvalidProductTypeCodeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InvalidProductTypeCodeExceptionTest {

    @Test
    public void createInvalidProductTypeCodeExceptionTest() {
        var ex = new InvalidProductTypeCodeException("Error");

        assertNotNull(ex);
    }

}
