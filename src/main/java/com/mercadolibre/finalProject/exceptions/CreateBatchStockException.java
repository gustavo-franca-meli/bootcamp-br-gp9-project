package com.mercadolibre.finalProject.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public class CreateBatchStockException extends ListException {
    private List<BatchCreateException> batchExceptions;

    public List<BatchCreateException> getBatchExceptions() {
        return batchExceptions;
    }

    public List<SubError> getSubErrors() {
        return batchExceptions.stream().map((a) -> (SubError) a).collect(Collectors.toList());
    }

    public void setBatchExceptions(List<BatchCreateException> batchExceptions) {
        this.batchExceptions = batchExceptions;
    }

    public CreateBatchStockException(String message, List<BatchCreateException> subErrors) {
        super(message, subErrors.stream().map((e) -> (SubError) e).collect(Collectors.toList()));
    }
}
