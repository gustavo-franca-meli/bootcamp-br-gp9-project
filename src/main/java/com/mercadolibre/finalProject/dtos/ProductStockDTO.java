package com.mercadolibre.finalProject.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductStockDTO {

    private Long productId;
    private String productName;
    private Double productPrice;
    private List<BatchDTO> batches;

    public ProductStockDTO(Long productId, String productName, Double productPrice, List<BatchDTO> batches) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.batches = batches;
    }
}
