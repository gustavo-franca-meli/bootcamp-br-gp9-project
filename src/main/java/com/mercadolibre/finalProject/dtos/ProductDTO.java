package com.mercadolibre.finalProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
public class ProductDTO {
    @NotNull
    @JsonProperty("productId")
    private Long id;
    @NotNull
    private Integer quantity;

    public ProductDTO(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
