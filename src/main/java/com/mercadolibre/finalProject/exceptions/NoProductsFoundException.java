package com.mercadolibre.finalProject.exceptions;

public class NoProductsFoundException extends RuntimeException{
    public NoProductsFoundException (String message) {
        super(message);
    }
}
