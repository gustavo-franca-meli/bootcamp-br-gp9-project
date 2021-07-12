package com.mercadolibre.finalProject.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReturnOrderRequestDTO {

    private Long purchaseOrderId;
    private String reason;
    private String buyerUsername;

    public ReturnOrderRequestDTO(Long purchaseOrderId, String reason) {
        this.purchaseOrderId = purchaseOrderId;
        this.reason = reason;
    }

}
