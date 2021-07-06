package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductPurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.PurchaseOrder;

import java.time.LocalDate;

public interface IProductBatchesPurchaseOrderService {
    ProductBatchesPurchaseOrderResponseDTO findById (Long id) throws ProductNotFoundException;
    ProductBatchesPurchaseOrderResponseDTO updateQuantity (Long id, PurchaseOrderUpdateRequestDTO updateRequest);
    ProductBatchesPurchaseOrderResponseDTO create (ProductPurchaseOrderRequestDTO productRequest, PurchaseOrder purchaseOrder, Long countryId, LocalDate date) throws ProductNotFoundException;
}
