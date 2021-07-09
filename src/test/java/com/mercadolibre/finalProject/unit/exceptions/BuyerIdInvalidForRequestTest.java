package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.BuyerIdInvalidForRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BuyerIdInvalidForRequestTest {

    @Test
    public void shouldBuyerIdInvalidForRequest() {
        var exception = new BuyerIdInvalidForRequest("Error");
        assertNotNull(exception);
    }
}
