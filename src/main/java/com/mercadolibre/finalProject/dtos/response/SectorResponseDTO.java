package com.mercadolibre.finalProject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorResponseDTO {

    private Long id;
    private Set<String> types = new HashSet<>();
    private Integer currentQuantityBatches;
    private Integer maxQuantityBatches;
}
