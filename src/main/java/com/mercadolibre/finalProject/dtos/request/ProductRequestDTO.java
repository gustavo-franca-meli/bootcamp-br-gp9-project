package com.mercadolibre.finalProject.dtos.request;

import lombok.Data;

@Data
public class ProductRequestDTO {

    private String name;
    private String description;
    private Double price;
    private Integer productType;
    private Long sellerId;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, String description, Double price, Integer productType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productType = productType;
    }
}
