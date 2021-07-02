package com.mercadolibre.finalProject.exceptions;

import com.mercadolibre.finalProject.dtos.BatchDTO;

public class BathCreateException extends Exception{
    public BathCreateException(BatchDTO batch, String message) {
        super("[ERROR] bath id "+ batch.getId() + " has a error : " + message);
    }
}
