package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.model.Seller;
import com.mercadolibre.finalProject.model.enums.ProductType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
