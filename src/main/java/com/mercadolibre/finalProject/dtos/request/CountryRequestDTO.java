package com.mercadolibre.finalProject.dtos.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
@Data
public class CountryRequestDTO {

    @NotNull(message = "Name es requerido")
    @Size(min = 3, message = "Name debe tener un minimo de 3 caracteres")
    private String name;

    public CountryRequestDTO(String name) {
        this.name = name;
    }
}
