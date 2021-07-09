package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.PurchaseOrderNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PurchaseOrderNotFoundExceptionTest {

    @Test
    public void shouldCreatePurchaseOrderNotFoundException() {
        var ex = new PurchaseOrderNotFoundException("message");

        assertNotNull(ex);
    }
}
