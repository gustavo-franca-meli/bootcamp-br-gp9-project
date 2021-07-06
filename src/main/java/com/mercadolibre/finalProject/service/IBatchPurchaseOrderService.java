package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;

public interface IBatchPurchaseOrderService {
    BatchPurchaseOrderResponseDTO findById (Long id);
    BatchPurchaseOrderResponseDTO updateQuantity (Long id, Integer newQuantity);
    BatchPurchaseOrderResponseDTO create(Long batchId, Integer quantity, ProductBatchesPurchaseOrder productBatchesPurchaseOrder);
}
