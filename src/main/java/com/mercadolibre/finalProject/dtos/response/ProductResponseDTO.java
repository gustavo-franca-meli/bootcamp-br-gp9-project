package com.mercadolibre.finalProject.dtos.response;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer type;

    public ProductResponseDTO(Long id, String name, String description, Double price, Integer type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

}
