package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.model.enums.ProductType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Set<Integer> types = new HashSet<>();

    public ProductResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductResponseDTO(Long id, String name, String description, Double price, Set<Integer> types) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.types = types;
    }

    public Set<ProductType> getProductTypes () {
        return this.types.stream().map(ProductType::toEnum).collect(Collectors.toSet());
    }
}
