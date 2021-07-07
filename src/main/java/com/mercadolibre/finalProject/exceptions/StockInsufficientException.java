package com.mercadolibre.finalProject.exceptions;

import java.util.List;

public class StockInsufficientException extends RuntimeException {
    private List<ProductStockInsufficientException> exceptions;

    public StockInsufficientException (String message, List<ProductStockInsufficientException> exceptions) {
        super(message);
        this.exceptions = exceptions;
    }
}
