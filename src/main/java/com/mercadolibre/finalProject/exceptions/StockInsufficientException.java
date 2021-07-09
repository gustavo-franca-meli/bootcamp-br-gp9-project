package com.mercadolibre.finalProject.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public class StockInsufficientException extends ListException {
    private List<ProductStockInsufficientException> exceptions;

    public StockInsufficientException (String message, List<ProductStockInsufficientException> subErrors) {
        super(message, subErrors.stream().map((e) -> (SubError) e).collect(Collectors.toList()));
    }
}