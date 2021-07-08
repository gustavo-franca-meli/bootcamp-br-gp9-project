package com.mercadolibre.finalProject.model.enums;

public enum ProductType {

//    PERISHABLE(1, "Perishable"),
//    NOT_PERISHABLE(2, "Not Perishable"),
//    FRAGILE(3, "Fragile");
    FRESH(1, "Fresh"),
    REFRIGERATED(2, "Refrigerated"),
    FROZEN(3, "Frozen");

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
        if(cod == null)
            return null;

        for(ProductType x : ProductType.values()) {
            if(cod.equals(x.getCod()))
                return x;
        }

        throw new RuntimeException("Invalid sector type code: "+ cod);
    }

    public static ProductType toCod (String description) {
        if(description == null)
            return null;

        for(ProductType x : ProductType.values()) {
            if(description.equals(x.getDescription()))
                return x;
        }

        throw new RuntimeException("Invalid sector type: " + description);
    }

}
