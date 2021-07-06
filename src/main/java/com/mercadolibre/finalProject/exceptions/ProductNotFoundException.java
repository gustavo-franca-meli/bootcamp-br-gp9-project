package com.mercadolibre.finalProject.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Product Not Found Exception");
    }
}
