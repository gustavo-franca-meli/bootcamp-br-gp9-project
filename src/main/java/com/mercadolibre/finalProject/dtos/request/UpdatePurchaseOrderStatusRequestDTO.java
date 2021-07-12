package com.mercadolibre.finalProject.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePurchaseOrderStatusRequestDTO {
    private Long purchaseOrderId;
    private Integer statusOrderCode;
    private String representativeUsername;

    public UpdatePurchaseOrderStatusRequestDTO(Long purchaseOrderId, Integer statusOrderCode) {
        this.purchaseOrderId = purchaseOrderId;
        this.statusOrderCode = statusOrderCode;
    }

    public UpdatePurchaseOrderStatusRequestDTO(Long purchaseOrderId, Integer statusOrderCode, String representativeUsername) {
        this.purchaseOrderId = purchaseOrderId;
        this.statusOrderCode = statusOrderCode;
        this.representativeUsername = representativeUsername;
    }
}
