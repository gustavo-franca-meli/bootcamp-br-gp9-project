package com.mercadolibre.finalProject.exceptions;

public class WarehouseNotFoundException extends Exception {
    public WarehouseNotFoundException() {
        super("Warehouse Not Found");
    }
}
