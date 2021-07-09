package com.mercadolibre.finalProject.exceptions;

public abstract class SubError extends RuntimeException{
    protected String message;

    public SubError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
