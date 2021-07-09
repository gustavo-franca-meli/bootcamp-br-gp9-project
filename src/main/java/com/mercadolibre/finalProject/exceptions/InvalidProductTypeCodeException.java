package com.mercadolibre.finalProject.exceptions;

public class InvalidProductTypeCodeException extends RuntimeException {
    public InvalidProductTypeCodeException (String message) {
        super(message);
    }
}
