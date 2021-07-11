package com.mercadolibre.finalProject.model.enums;

public enum ReturnOrderStatusType {

    PROCESSING(1, "Processing"),
    ACCEPTED(2, "Accepted"),
    REFUSED(3, "Refused"),
    CANCELED(4, "Canceled");

    private int cod;
    private String description;

    ReturnOrderStatusType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static ReturnOrderStatusType toEnum(Integer cod) {
        if (cod == null)
            return null;

        for (ReturnOrderStatusType x : ReturnOrderStatusType.values()) {
            if (cod.equals(x.getCod()))
                return x;
        }

        throw new RuntimeException("Invalid order status code: " + cod);
    }
}
