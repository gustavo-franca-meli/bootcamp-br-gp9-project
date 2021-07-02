package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.dtos.BatchDto;

import java.util.List;

public class InboundOrderResponseDTO {
    List<BatchDto> batchStock;

    public InboundOrderResponseDTO(List<BatchDto> batchStock) {
        this.batchStock = batchStock;
    }
}
