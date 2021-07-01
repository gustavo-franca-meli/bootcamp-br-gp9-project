package com.mercadolibre.finalProject.exceptions;

public class NoSpaceInSectorException extends Exception{

    public NoSpaceInSectorException(String message) {
        super(message);
    }

    public NoSpaceInSectorException(String message, Throwable cause) {
        super(message,cause);
    }
}
