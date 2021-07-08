package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderMapper {

    static PurchaseOrderResponseDTO toResponseDTO (PurchaseOrder purchaseOrder) {
        return new PurchaseOrderResponseDTO(
                purchaseOrder.getId(),
                purchaseOrder.getOrderDate(),
                purchaseOrder.getTotalPrice(),
                ProductBatchesPurchaseOrderMapper.toListResponseDTO(purchaseOrder.getProducts()));
    }
}
