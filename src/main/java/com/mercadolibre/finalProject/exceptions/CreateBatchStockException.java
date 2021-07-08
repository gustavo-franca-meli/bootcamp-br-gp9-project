package com.mercadolibre.finalProject.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public class CreateBatchStockException extends ListException {

    public CreateBatchStockException (String message, List<BatchCreateException> subErrors) {
        super(message, subErrors.stream().map((e) -> (SubError) e).collect(Collectors.toList()));
    }
}
