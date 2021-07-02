package com.mercadolibre.finalProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto {
    @JsonProperty("sectionCode")
    @NotNull
    @NotEmpty
    private String code;
    @NotNull
    @NotEmpty
    private String warehouseCode;


}
