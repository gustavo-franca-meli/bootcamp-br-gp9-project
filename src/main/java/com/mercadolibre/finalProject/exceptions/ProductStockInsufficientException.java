package com.mercadolibre.finalProject.exceptions;

public class ProductStockInsufficientException extends RuntimeException {
    public ProductStockInsufficientException(String message) { super(message); }
}
