package com.mercadolibre.finalProject.exceptions;

import java.util.List;

public class CreateBathStockException extends Exception {
    private List<BathCreateException> bathExceptions;
    public CreateBathStockException(String message, List<BathCreateException> bathExceptions) {
        super(message);
        this.bathExceptions = bathExceptions;
    }

    public List<BathCreateException> getBathExceptions() {
        return bathExceptions;
    }

    public void setBathExceptions(List<BathCreateException> bathExceptions) {
        this.bathExceptions = bathExceptions;
    }


}
