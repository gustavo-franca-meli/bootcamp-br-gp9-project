package com.mercadolibre.finalProject.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequestDTO {

    private String name;

    private List<Integer> types;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, List<Integer> types) {
        this.name = name;
        this.types = types;
    }
}
