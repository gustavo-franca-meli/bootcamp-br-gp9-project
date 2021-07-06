package com.mercadolibre.finalProject.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderUpdateRequestDTO {

    private Long purchaseOrderId;
    private Long productId;
    private Integer newQuantity;

    public PurchaseOrderUpdateRequestDTO(Long purchaseOrderId, Long productId, Integer newQuantity) {
        this.purchaseOrderId = purchaseOrderId;
        this.productId = productId;
        this.newQuantity = newQuantity;
    }
}
