package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;

import java.util.List;
import java.util.stream.Collectors;

public interface ProductBatchesPurchaseOrderMapper {
    static ProductBatchesPurchaseOrderResponseDTO toResponseDTO (ProductBatchesPurchaseOrder productBatchesPurchaseOrder, String productName, List<BatchPurchaseOrderResponseDTO> batches) {
        return new ProductBatchesPurchaseOrderResponseDTO(
                productBatchesPurchaseOrder.getId(),
                productBatchesPurchaseOrder.getProductId(),
                productName,
                productBatchesPurchaseOrder.getTotalQuantity(),
                productBatchesPurchaseOrder.getTotalPrice(),
                batches);
    }

    static List<ProductBatchesPurchaseOrderResponseDTO> toListResponseDTO (List<ProductBatchesPurchaseOrder> productBatches,String productName, List<BatchPurchaseOrderResponseDTO> batches) {
        return productBatches.stream().map(p -> ProductBatchesPurchaseOrderMapper.toResponseDTO(p,productName,batches)).collect(Collectors.toList());
    }
}
