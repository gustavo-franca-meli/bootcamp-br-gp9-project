package com.mercadolibre.finalProject.exceptions;

public class BuyerIdInvalidForRequest extends RuntimeException{
    public BuyerIdInvalidForRequest(String message) {
        super(message);
    }
}
