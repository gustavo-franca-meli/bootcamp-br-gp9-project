package com.mercadolibre.finalProject.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private String error;
    private String message;
    private Integer status;
    private List<SubError> errors;

    public ApiError() {
    }

    public ApiError(String error, String message, Integer status) {
        super();
        this.error = error;
        this.message = message;
        this.status = status;
    }

    public ApiError(String error, String message, Integer status, List<SubError> errors) {
        this.error = error;
        this.message = message;
        this.status = status;
        this.errors = errors;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SubError> getErrors() {
        return errors;
    }

    public void setErrors(List<SubError> errors) {
        this.errors = errors;
    }

    public void addError(SubError e) {
        if (errors == null)
            errors = new ArrayList<>();
        errors.add(e);
    }
}
