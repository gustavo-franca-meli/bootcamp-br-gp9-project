package com.mercadolibre.finalProject.dtos.request;

import lombok.Data;

@Data
public class ReturnOrderRequestDTO {

    private final Long purchaseOrderId;
    private final String reason;
    private String buyerUsername;

    public ReturnOrderRequestDTO(Long purchaseOrderId, String reason) {
        this.purchaseOrderId = purchaseOrderId;
        this.reason = reason;
    }

}
