package com.mercadolibre.finalProject.dtos.request;

import lombok.Data;

@Data
public class SectorBatchRequestDTO {

    private final Long productId;
    private final String username;
    private final String ordered;

    public SectorBatchRequestDTO(Long productId, String username, String ordered) {
        this.productId = productId;
        this.username = username;
        this.ordered = ordered;
    }
}
