package com.mercadolibre.finalProject.model.enums;

public enum SectorType {

    PERISHABLE(1, "Perishable"),
    NOT_PERISHABLE(2, "Not Perishable"),
    FRAGILE(3, "Fragile");

    private int cod;
    private String description;

    SectorType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static SectorType toEnum(Integer cod) {
        if(cod == null)
            return null;

        for(SectorType x : SectorType.values()) {
            if(cod.equals(x.getCod()))
                return x;
        }

        throw new RuntimeException("Invalid sector type code: "+ cod);
    }

    public static SectorType toCod (String description) {
        if(description == null)
            return null;

        for(SectorType x : SectorType.values()) {
            if(description.equals(x.getDescription()))
                return x;
        }

        throw new RuntimeException("Invalid sector type: " + description);
    }

}
