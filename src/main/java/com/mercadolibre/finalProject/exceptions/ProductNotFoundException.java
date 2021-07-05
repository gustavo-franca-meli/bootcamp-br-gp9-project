package com.mercadolibre.finalProject.exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
        super("Product Not Found Exception");
    }
}
