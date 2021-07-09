package com.mercadolibre.finalProject.dtos.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPurchaseOrderRequestDTO {

    private Long productId;
    private Integer quantity;
}
