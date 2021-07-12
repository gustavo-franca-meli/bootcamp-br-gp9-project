package com.mercadolibre.finalProject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryResponseDTO {
    private String productName;
    private String warehouseName;
    private String quantityInStock;
    private String unitaryPrice;
    private String totalAssetsValue;
}
