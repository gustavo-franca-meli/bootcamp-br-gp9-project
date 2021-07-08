package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.SellerNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SellerNotFoundExceptionTest {

    @Test
    public void shouldCreateSellerNotFoundException() {
        var exception = new SellerNotFoundException();
        assertNotNull(exception);
    }

}
