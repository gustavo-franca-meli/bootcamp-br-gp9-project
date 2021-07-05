package com.mercadolibre.finalProject.exceptions;

import com.mercadolibre.finalProject.dtos.BatchDTO;

public class BatchCreateException extends Exception{
    public BatchCreateException(BatchDTO batch, String message) {
        super("[ERROR] bath id "+ batch.getId() + " has a error : " + message);
    }
}
