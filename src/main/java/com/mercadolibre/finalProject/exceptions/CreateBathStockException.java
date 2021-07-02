package com.mercadolibre.finalProject.exceptions;

import java.util.List;

public class CreateBathStockException extends Exception {
    private List<BathException> bathExceptions;
    public CreateBathStockException(String message, List<BathException> bathExceptions) {
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
