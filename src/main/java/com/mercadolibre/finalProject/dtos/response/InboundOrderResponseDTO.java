package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InboundOrderResponseDTO {
    Long orderId;
    List<BatchDTO> batchStock;

    public InboundOrderResponseDTO(Long orderId, List<BatchDTO> batchStock) {
        this.orderId = orderId;
        this.batchStock = batchStock;
    }
}
