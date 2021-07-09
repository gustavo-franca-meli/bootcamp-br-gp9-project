package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.BatchPurchaseOrder;
import com.mercadolibre.finalProject.model.PurchaseOrder;

import java.util.List;
import java.util.stream.Collectors;

public interface PurchaseOrderMapper {
    static PurchaseOrderResponseDTO toResponseDTO (PurchaseOrder purchaseOrder) {
        return new PurchaseOrderResponseDTO(
                purchaseOrder.getId(),
                purchaseOrder.getOrderDate(),
                purchaseOrder.getTotalPrice(),
                ProductBatchesPurchaseOrderMapper.toListResponseDTO(purchaseOrder.getProducts()));
    }

    static List<PurchaseOrderResponseDTO> toListResponseDTO (List<PurchaseOrder> purchaseOrders) {
        return purchaseOrders.stream().map(PurchaseOrderMapper::toResponseDTO).collect(Collectors.toList());
    }
}
