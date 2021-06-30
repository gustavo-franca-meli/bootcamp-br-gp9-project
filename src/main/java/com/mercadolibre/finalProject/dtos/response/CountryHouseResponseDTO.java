package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.dtos.CountryHouseDTO;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class CountryHouseResponseDTO {
    private String message;
    private CountryHouseDTO countryHouseDTO;
}
