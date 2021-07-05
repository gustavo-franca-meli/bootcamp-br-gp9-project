package com.mercadolibre.finalProject.dtos.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDTO {

    @NotNull
    private String name;

    private Long sellerId;

    public ProductRequestDTO(String name, Long sellerId) {
        this.name = name;
        this.sellerId = sellerId;
    }
}
