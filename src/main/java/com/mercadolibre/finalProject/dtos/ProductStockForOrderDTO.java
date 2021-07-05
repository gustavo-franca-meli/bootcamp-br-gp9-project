package com.mercadolibre.finalProject.dtos;

import com.mercadolibre.finalProject.model.Batch;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductStockForOrderDTO {
    private Long productId;
    private String productName;
    private Integer orderQuantity;
    private List<Batch> batches;

    public ProductStockForOrderDTO(Long productId, String productName, Integer orderQuantity, List<Batch> batches) {
        this.productId = productId;
        this.productName = productName;
        this.orderQuantity = orderQuantity;
        this.batches = batches;
    }
}
