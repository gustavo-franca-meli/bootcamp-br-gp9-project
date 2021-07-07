package com.mercadolibre.finalProject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorResponseDTO {

    private Long id;
    private String sectorType;
    private Integer currentQuantityBatches;
    private Integer maxQuantityBatches;
    private Long warehouseId;

    public SectorResponseDTO(Long id, String sectorType, Integer currentQuantityBatches, Integer maxQuantityBatches) {
        this.id = id;
        this.sectorType = sectorType;
        this.currentQuantityBatches = currentQuantityBatches;
        this.maxQuantityBatches = maxQuantityBatches;
    }
}
