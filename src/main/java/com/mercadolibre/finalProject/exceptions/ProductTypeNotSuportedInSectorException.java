package com.mercadolibre.finalProject.exceptions;

public class ProductTypeNotSuportedInSectorException extends Exception {
    public ProductTypeNotSuportedInSectorException(String productId,String type,String sectorId) {
        super("Product" + productId + "with  Type" + type +"not suported in sector"+ sectorId );
    }
}
