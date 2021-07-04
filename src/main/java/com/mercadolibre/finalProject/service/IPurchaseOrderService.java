package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.PurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;

public interface IPurchaseOrderService {
    PurchaseOrderResponseDTO create (PurchaseOrderDTO purchaseOrder, String representative) throws WarehouseNotFoundException;
}
