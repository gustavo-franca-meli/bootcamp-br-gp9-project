package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.ValidationError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ValidationErrorTest {

    @Test
    void shoudlCreateValidationError() {
        var ex = new ValidationError("field", "message");

        assertNotNull(ex);
    }

    @Test
    void shoudlAndGetValidationError() {
        var ex = new ValidationError("field", "message");

        assertNotNull(ex.getMessage());
        assertNotNull(ex.getField());
    }
}
