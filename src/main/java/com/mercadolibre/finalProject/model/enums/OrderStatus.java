package com.mercadolibre.finalProject.model.enums;

public enum OrderStatus {

    PROCESSING (1, "Processing"),
    PAYED (2, "Payment Approved"),
    IN_DELIVERY (3, "In Delivery"),
    DELIVERED (4, "Delivered");

    private int cod;
    private String description;

    OrderStatus(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static OrderStatus toEnum(Integer cod) {
        if(cod == null)
            return null;

        for(OrderStatus x : OrderStatus.values()) {
            if(cod.equals(x.getCod()))
                return x;
        }

        throw new RuntimeException("Invalid order status code: " + cod);
    }
}
