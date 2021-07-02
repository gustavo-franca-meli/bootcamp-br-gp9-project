package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import lombok.Data;

import java.util.List;

@Data
public class InboundOrderResponseDTO {
    List<BatchDTO> batchStock;

    public InboundOrderResponseDTO(List<BatchDTO> batchStock) {
        this.batchStock = batchStock;
    }
}
