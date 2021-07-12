package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TransferOrderResponseDTO {
    Long transferOrderId;
    List<BatchDTO> batchStock;
    public TransferOrderResponseDTO(Long orderId, List<BatchDTO> batchStock) {
        this.transferOrderId = orderId;
        this.batchStock = batchStock;
    }
}
