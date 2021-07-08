package com.mercadolibre.finalProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.finalProject.model.Sector;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SectorDTO {
    @JsonProperty("sectionCode")
    @NotNull
    private Long code;

    @NotNull
    private Long warehouseCode;

    @NotNull
    private Double currentQuantityBatches;

    @NotNull
    private Double maxQuantityBatches;

    public SectorDTO(Long code, Long warehouseCode, Double currentQuantityBatches, Double maxQuantityBatches) {
        this.code = code;
        this.warehouseCode = warehouseCode;
        this.currentQuantityBatches = currentQuantityBatches;
        this.maxQuantityBatches = maxQuantityBatches;
    }

    public SectorDTO(Long code, Long warehouseCode) {
        this.setCode(code);
        this.setWarehouseCode(warehouseCode);
    }
}
