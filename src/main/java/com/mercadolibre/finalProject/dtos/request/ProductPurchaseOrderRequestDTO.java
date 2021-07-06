package com.mercadolibre.finalProject.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPurchaseOrderRequestDTO {

    private Long productId;
    private Integer quantity;

    public ProductPurchaseOrderRequestDTO(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
