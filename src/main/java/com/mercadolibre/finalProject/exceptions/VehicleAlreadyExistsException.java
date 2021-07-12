package com.mercadolibre.finalProject.exceptions;

public class VehicleAlreadyExistsException extends RuntimeException {

    public VehicleAlreadyExistsException(String plate) {
        super("Vehicle with the plate " + plate + " already exists");
    }
}
