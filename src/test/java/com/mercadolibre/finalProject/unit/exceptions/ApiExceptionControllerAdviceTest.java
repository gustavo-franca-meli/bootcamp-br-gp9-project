package com.mercadolibre.finalProject.unit.exceptions;

import com.mercadolibre.finalProject.exceptions.ApiExceptionControllerAdvice;
import com.mercadolibre.finalProject.exceptions.NoSpaceInSectorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ApiExceptionControllerAdviceTest {

    private static final String MESSAGE_ERROR = "Error";

    private ApiExceptionControllerAdvice apiExceptionControllerAdvice = new ApiExceptionControllerAdvice();
    private Exception ex = null;

    @BeforeEach
    public void setUp() {
        ex = new Exception(MESSAGE_ERROR);
    }

    @Test
    void shouldReturnApiErrorForHandlerException() {
        var response = apiExceptionControllerAdvice.handleException(ex);
        assertEquals(response.getMessage(), MESSAGE_ERROR);
    }

    @Test
    void shouldReturnApiErrorForBadRequestException() {
        var response = apiExceptionControllerAdvice.badRequest(ex);
        assertEquals(response.getMessage(), MESSAGE_ERROR);
    }

    @Test
    void shouldReturnApiErrorForNotFound() {
        var response = apiExceptionControllerAdvice.notFoundRequest(ex);
        assertEquals(response.getMessage(), MESSAGE_ERROR);
    }

    @Test
    void shouldReturnApiErrorForNoSpaceSectorException() {
        var exception = new NoSpaceInSectorException("Error");
        var response = apiExceptionControllerAdvice.noSpaceSectorException(exception);
        assertEquals(response.getMessage(), MESSAGE_ERROR);
    }

}
