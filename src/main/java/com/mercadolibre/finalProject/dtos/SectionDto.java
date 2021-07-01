package com.mercadolibre.finalProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SectionDto {
    @JsonProperty("sectionCode")
    @NotNull
    @NotEmpty
    private String code;
    @NotNull
    @NotEmpty
    private String warehouseCode;
}
