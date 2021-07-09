package com.mercadolibre.finalProject.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ListException extends Exception {
    private List<SubError> subErrors;
    public ListException(String message, List<SubError> subErrors) {
        super(message);
        this.subErrors = subErrors;
    }

    public List<SubError> getSubErros() {
        return subErrors.stream().map((a)-> (SubError)a).collect(Collectors.toList());
    }

    public void setBathExceptions(List<SubError> subErrors) {
        this.subErrors = subErrors;
    }
}