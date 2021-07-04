package com.mercadolibre.finalProject.exceptions;

import java.util.List;

public class CreateBatchStockException extends Exception {
    private List<BathException> bathExceptions;
    public CreateBatchStockException(String message, List<BathException> bathExceptions) {
        super(message);
        this.bathExceptions = bathExceptions;
    }

    public List<BathException> getBathExceptions() {
        return bathExceptions;
    }

    public void setBathExceptions(List<BathException> bathExceptions) {
        this.bathExceptions = bathExceptions;
    }


}
