package com.mercadolibre.finalProject.config;

import com.mercadolibre.finalProject.exceptions.*;
import com.newrelic.api.agent.NewRelic;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException ex) {
        ApiError apiError = new ApiError("route_not_found", String.format("Route %s not found", req.getRequestURI()), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<ApiError> handleApiException(ApiException e) {
        Integer statusCode = e.getStatusCode();
        boolean expected = HttpStatus.INTERNAL_SERVER_ERROR.value() > statusCode;
        NewRelic.noticeError(e, expected);
        if (expected) {
            LOGGER.warn("Internal Api warn. Status Code: " + statusCode, e);
        } else {
            LOGGER.error("Internal Api error. Status Code: " + statusCode, e);
        }

        ApiError apiError = new ApiError(e.getCode(), e.getDescription(), statusCode);
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ApiError> handleUnknownException(Exception e) {
        LOGGER.error("Internal error", e);
        NewRelic.noticeError(e);

        ApiError apiError = new ApiError("internal_error", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {WarehouseNotFoundException.class, RepresentativeNotFound.class, SectorNotFoundException.class, NotFoundException.class,InboundOrderNotFoundException.class, ProductNotFoundException.class, BatchNotFoundException.class})
    protected ResponseEntity<ApiError> handleNotFoundException(Exception e) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.toString(), e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(CreateBatchStockException.class)
    protected ResponseEntity<ApiError> handleCreateBatchStockException(CreateBatchStockException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), HttpStatus.BAD_REQUEST.value(), e.getSubErros());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);

    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ApiError> handleBadRequestException(BadRequestException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);

    }
}