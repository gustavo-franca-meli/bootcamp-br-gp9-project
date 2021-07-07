package com.mercadolibre.finalProject.exceptions;

import com.mercadolibre.finalProject.dtos.BatchDTO;

public class BatchCreateException extends SubError {

    public BatchCreateException(Long position, String message) {
        super("[ERROR] create batch position "+ position + " error: " + message);
    }
}
