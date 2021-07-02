package com.mercadolibre.finalProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.finalProject.model.Sector;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
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

    public SectorDTO(@NotNull Sector sector) {
        this.code = sector.getId();
        this.warehouseCode = sector.getWarehouse().getId();
        this.currentQuantityBatches = sector.getCurrentQuantityBatches();
        this.maxQuantityBatches = sector.getMaxQuantityBatches();
    }
}
