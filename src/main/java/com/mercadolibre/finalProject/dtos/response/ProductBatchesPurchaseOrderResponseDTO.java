package com.mercadolibre.finalProject.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductBatchesPurchaseOrderResponseDTO {

    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private List<BatchPurchaseOrderResponseDTO> batches;

    public ProductBatchesPurchaseOrderResponseDTO(Long productId, String productName, Integer quantity, Double price, List<BatchPurchaseOrderResponseDTO> batches) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.batches = batches;
    }

    public ProductBatchesPurchaseOrderResponseDTO(Long id, Long productId, String productName, Integer quantity, Double price, List<BatchPurchaseOrderResponseDTO> batches) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.batches = batches;
    }

    public ProductBatchesPurchaseOrderResponseDTO() {
    }

}
