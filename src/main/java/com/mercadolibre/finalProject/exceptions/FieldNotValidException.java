package com.mercadolibre.finalProject.exceptions;


public class FieldNotValidException extends  SubError {
    private String field ;
    private String Object;

    public FieldNotValidException(String message, String field, String object) {
        super(message);
        this.field = field;
        Object = object;
    }

    public String getField() {
        return field;
    }

    public String getObject() {
        return Object;
    }
}
