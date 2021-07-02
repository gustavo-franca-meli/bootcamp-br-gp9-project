package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.dtos.BatchDTO;

import java.util.List;

public class InboundOrderResponseDTO {
    List<BatchDTO> batchStock;

    public InboundOrderResponseDTO(List<BatchDTO> batchStock) {
        this.batchStock = batchStock;
    }
}
