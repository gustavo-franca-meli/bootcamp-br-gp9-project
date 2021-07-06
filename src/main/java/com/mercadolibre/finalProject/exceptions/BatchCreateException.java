package com.mercadolibre.finalProject.exceptions;

import com.mercadolibre.finalProject.dtos.BatchDTO;

public class BatchCreateException extends SubError {

    public BatchCreateException(Long batchId, String message) {
        super("[ERROR] create batch id "+ batchId + " error: " + message);
    }
}
