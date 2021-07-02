package com.mercadolibre.finalProject.exceptions;

public class NoSpaceInSectorException extends RuntimeException{

    public NoSpaceInSectorException(String message) {
        super(message);
    }

    public NoSpaceInSectorException(String message, Throwable cause) {
        super(message,cause);
    }
}
