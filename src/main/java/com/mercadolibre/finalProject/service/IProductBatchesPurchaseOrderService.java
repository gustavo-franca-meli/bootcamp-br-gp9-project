package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.PurchaseOrder;

public interface IProductBatchesPurchaseOrderService {
    ProductBatchesPurchaseOrderResponseDTO findById (Long id);
}
