package com.mercadolibre.finalProject.exceptions;

public class PurchaseOrderNotFoundException extends RuntimeException {
    public PurchaseOrderNotFoundException(String message) {
        super(message);
    }
}
