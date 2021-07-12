package com.mercadolibre.finalProject.exceptions;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(Long id) {
        super("Vehicle with id " + id + " not found");
    }

    public VehicleNotFoundException(String plate) {
        super("Vehicle with plate " + plate + " not found");
    }
}
