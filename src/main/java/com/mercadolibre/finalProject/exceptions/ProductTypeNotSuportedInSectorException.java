package com.mercadolibre.finalProject.exceptions;

import com.mercadolibre.finalProject.model.enums.ProductType;

import java.util.Set;

public class ProductTypeNotSuportedInSectorException extends Exception {
    public ProductTypeNotSuportedInSectorException(String productId, Set<ProductType> type, String sectorId) {
        super("Product " + productId + " with  Type" + type.stream().findFirst().get().getDescription() +" not supported in sector "+ sectorId );
    }
}
