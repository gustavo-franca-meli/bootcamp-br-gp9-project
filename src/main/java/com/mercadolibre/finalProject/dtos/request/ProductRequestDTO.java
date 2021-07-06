package com.mercadolibre.finalProject.dtos.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequestDTO {

    private String name;
    private String description;
    private Double price;
    private List<Integer> types;
    private Long sellerId;

    public ProductRequestDTO(String name, String description, Double price, List<Integer> types) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.types = types;
    }
}
