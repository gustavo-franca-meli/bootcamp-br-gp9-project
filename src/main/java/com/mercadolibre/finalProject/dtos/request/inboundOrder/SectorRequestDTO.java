package com.mercadolibre.finalProject.dtos.request.inboundOrder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
public class SectorRequestDTO {
    @JsonProperty("sectionCode")
    @NotNull
    private Long code;

    @NotNull
    private Long warehouseCode;

    public SectorRequestDTO(@NotNull Long code, @NotNull Long warehouseCode) {
        this.code = code;
        this.warehouseCode = warehouseCode;
    }

}
