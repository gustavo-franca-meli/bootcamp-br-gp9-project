package com.mercadolibre.finalProject.dtos.request;

import lombok.Data;

@Data
public class SectorBatchRequestDTO {

    private final Long productId;
    private final Long representativeId;
    private final String ordered;

    public SectorBatchRequestDTO(Long productId, Long representativeId, String ordered) {
        this.productId = productId;
        this.representativeId = representativeId;
        this.ordered = ordered;
    }
}
