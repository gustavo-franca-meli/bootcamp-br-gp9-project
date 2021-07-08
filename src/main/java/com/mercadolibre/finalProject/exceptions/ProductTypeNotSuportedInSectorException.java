package com.mercadolibre.finalProject.exceptions;

public class ProductTypeNotSuportedInSectorException extends RuntimeException {
    public ProductTypeNotSuportedInSectorException(Long productId, String productTypeDescription, Long sectorId) {
        super("Product " + productId + " with type " + productTypeDescription + " not supported in sector " + sectorId);
    }
}
