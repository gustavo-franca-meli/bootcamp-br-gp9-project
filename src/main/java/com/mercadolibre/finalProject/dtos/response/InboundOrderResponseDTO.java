package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import lombok.Data;

import java.util.List;

@Data
public class InboundOrderResponseDTO {
    Long orderId;
    List<BatchDTO> batchStock;

    public InboundOrderResponseDTO(Long orderId, List<BatchDTO> batchStock) {
        this.orderId = orderId;
        this.batchStock = batchStock;
    }
}
