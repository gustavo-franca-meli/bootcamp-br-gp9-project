package com.mercadolibre.finalProject.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockForOrderDTO {

    private Integer orderQuantity;
    private ProductStockDTO productStock;

    public StockForOrderDTO(Integer orderQuantity, ProductStockDTO productStock) {
        this.orderQuantity = orderQuantity;
        this.productStock = productStock;
    }
}
