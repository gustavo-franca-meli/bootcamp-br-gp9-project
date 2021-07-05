package com.mercadolibre.finalProject.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class SectorResponseDTO {

    private Long id;
    private Set<String> types = new HashSet<>();
    private Double currentQuantityBatches;
    private Double maxQuantityBatches;

    public SectorResponseDTO(Long id, Set<String> types, Double currentQuantityBatches, Double maxQuantityBatches) {
        this.id = id;
        this.types = types;
        this.currentQuantityBatches = currentQuantityBatches;
        this.maxQuantityBatches = maxQuantityBatches;
    }
}
