package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;

public interface IBatchPurchaseOrderService {
    BatchPurchaseOrderResponseDTO findById (Long id);
}
