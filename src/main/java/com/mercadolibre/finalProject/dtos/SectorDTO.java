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
    @NotEmpty
    private String code;

    @NotNull
    @NotEmpty
    private String warehouseCode;

    @NotNull
    @NotEmpty
    private Double currentQuantityBatches;

    @NotNull
    @NotEmpty
    private Double maxQuantityBatches;

    public SectorDTO(String code, String warehouseCode, Double currentQuantityBatches, Double maxQuantityBatches) {
        this.code = code;
        this.warehouseCode = warehouseCode;
        this.currentQuantityBatches = currentQuantityBatches;
        this.maxQuantityBatches = maxQuantityBatches;
    }

    public SectorDTO(Sector sector) {
        this.code = sector.getId().toString();
        this.warehouseCode = sector.getWarehouse().getId().toString();
        this.currentQuantityBatches = sector.getCurrentQuantityBatches();
        this.maxQuantityBatches = sector.getMaxQuantityBatches();
    }
}
