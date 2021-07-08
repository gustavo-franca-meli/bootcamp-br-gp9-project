package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;

import java.util.List;
import java.util.stream.Collectors;

public interface ProductBatchesPurchaseOrderMapper {
    static ProductBatchesPurchaseOrderResponseDTO toResponseDTO (ProductBatchesPurchaseOrder productBatchesPurchaseOrder) {
        return new ProductBatchesPurchaseOrderResponseDTO(
                productBatchesPurchaseOrder.getId(),
                productBatchesPurchaseOrder.getProduct().getId(),
                productBatchesPurchaseOrder.getProduct().getName(),
                productBatchesPurchaseOrder.getTotalQuantity(),
                productBatchesPurchaseOrder.getTotalPrice(),
                BatchPurchaseOrderMapper.toListResponseDTO(productBatchesPurchaseOrder.getPurchaseBatches()));
    }

    static List<ProductBatchesPurchaseOrderResponseDTO> toListResponseDTO (List<ProductBatchesPurchaseOrder> productBatches) {
        return productBatches.stream().map(ProductBatchesPurchaseOrderMapper::toResponseDTO).collect(Collectors.toList());
    }
}
