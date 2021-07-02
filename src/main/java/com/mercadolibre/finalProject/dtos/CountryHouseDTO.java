package com.mercadolibre.finalProject.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Validated
@Data
public class CountryHouseDTO {

    @JsonIgnore
    private UUID id;

    @NotNull(message = "Name es requerido")
    @Size(min = 3, message = "Name debe tener un minimo de 3 caracteres")
    private String name;

    @NotNull(message = "Country es requerido")
    @Size(min = 3, message = "Country debe tener un minimo de 3 caracteres")
    private String country;

    @JsonIgnore
    private List<AccountResponseDTO> accounts;
}
