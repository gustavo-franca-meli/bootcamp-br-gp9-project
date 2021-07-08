package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.InternalServerErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InternalServerErrorExceptionTest {

    @Test
    public void shouldCreateInternalServerErrorExceptionTest() {
        var ex = new InternalServerErrorException(new Throwable());
        assertNotNull(ex);
    }

    @Test
    public void shouldCreateInternalServerErrorExceptionTestWithMessage() {
        var ex = new InternalServerErrorException("Error", new Throwable());
        assertNotNull(ex);
    }

}
