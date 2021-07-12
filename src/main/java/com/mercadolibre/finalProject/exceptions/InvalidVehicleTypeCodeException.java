package com.mercadolibre.finalProject.exceptions;

public class InvalidVehicleTypeCodeException extends RuntimeException {
    public InvalidVehicleTypeCodeException(Integer cod) {
        super("Invalid vehicle type code: " + cod);
    }
}
