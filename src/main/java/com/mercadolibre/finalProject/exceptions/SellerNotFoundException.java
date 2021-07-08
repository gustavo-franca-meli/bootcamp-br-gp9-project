package com.mercadolibre.finalProject.exceptions;

public class SellerNotFoundException extends RuntimeException {

    public SellerNotFoundException() {
        super("Seller Not Found Exception");
    }

}
