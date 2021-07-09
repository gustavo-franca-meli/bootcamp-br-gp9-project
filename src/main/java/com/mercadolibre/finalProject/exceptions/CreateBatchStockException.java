package com.mercadolibre.finalProject.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public class CreateBatchStockException extends Exception {
    private List<BatchCreateException> batchExceptions;
    public CreateBatchStockException(String message, List<BatchCreateException> batchExceptions) {
        super(message);
        this.batchExceptions = batchExceptions;
    }

    public List<BatchCreateException> getBatchExceptions() {
        return batchExceptions;
    }

    public List<SubError> getSubErrors() {
        return batchExceptions.stream().map((a)-> (SubError)a).collect(Collectors.toList());
    }


    public void setBatchExceptions(List<BatchCreateException> batchExceptions) {
        this.batchExceptions = batchExceptions;
    }

}
