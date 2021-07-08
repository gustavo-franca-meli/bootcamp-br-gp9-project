package com.mercadolibre.finalProject.dtos.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderUpdateRequestDTO {

    private Long purchaseOrderId;
    private Long productId;
    private Integer newQuantity;

}
