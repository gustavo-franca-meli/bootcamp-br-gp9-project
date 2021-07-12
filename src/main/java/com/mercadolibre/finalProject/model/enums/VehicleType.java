package com.mercadolibre.finalProject.model.enums;

import com.mercadolibre.finalProject.exceptions.InvalidVehicleTypeCodeException;

public enum VehicleType {

    MOTORCYCLE(1, "Motorcycle"),
    CAR(2, "Car"),
    VAN(3, "Van"),
    TRUCK(3, "Truck");

    private int cod;
    private String description;

    VehicleType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static VehicleType toEnum(Integer cod) {
        if (cod == null)
            return null;

        for (VehicleType x : VehicleType.values()) {
            if (cod.equals(x.getCod()))
                return x;
        }

        throw new InvalidVehicleTypeCodeException(cod);
    }

}
