package com.mercadolibre.finalProject.exceptions;

public class BatchCreateException extends SubError {

    public BatchCreateException(Long position, String message) {
        super("[ERROR] create batch position "+ position + " error: " + message);
    }
}
