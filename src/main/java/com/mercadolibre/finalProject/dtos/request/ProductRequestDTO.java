package com.mercadolibre.finalProject.dtos.request;

public class ProductRequestDTO {

    private String name;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
