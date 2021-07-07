package com.mercadolibre.finalProject.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public class CreateBatchStockException extends Exception {
    private List<BatchCreateException> bathExceptions;
    public CreateBatchStockException(String message, List<BatchCreateException> bathExceptions) {
        super(message);
        this.bathExceptions = bathExceptions;
    }

    public List<BatchCreateException> getBathExceptions() {
        return bathExceptions;
    }

    public List<SubError> getSubErros() {
        return bathExceptions.stream().map((a)-> (SubError)a).collect(Collectors.toList());
    }


    public void setBathExceptions(List<BatchCreateException> bathExceptions) {
        this.bathExceptions = bathExceptions;
    }


}
