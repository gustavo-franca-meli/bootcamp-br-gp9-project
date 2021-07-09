package com.mercadolibre.finalProject.model.enums;

import com.mercadolibre.finalProject.exceptions.InvalidProductTypeCodeException;

public enum ProductType {

    PERISHABLE (1, "Perishable"),
    NOT_PERISHABLE (2, "Not Perishable"),
    FRAGILE (3, "Fragile"),
    REFRIGERATED (4, "Refrigerated");

    private int cod;
    private String description;

    ProductType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static ProductType toEnum(Integer cod) {
        if (cod == null)
            return null;

        for (ProductType x : ProductType.values()) {
            if (cod.equals(x.getCod()))
                return x;
        }

        throw new InvalidProductTypeCodeException("Invalid sector type code: " + cod);
    }
}
