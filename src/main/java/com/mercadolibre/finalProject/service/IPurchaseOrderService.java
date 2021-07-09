package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.PurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.exceptions.StockInsufficientException;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;

import java.util.List;

public interface IPurchaseOrderService {
    PurchaseOrderResponseDTO create(PurchaseOrderRequestDTO purchaseOrder, String username) throws WarehouseNotFoundException, ProductNotFoundException, StockInsufficientException;

    PurchaseOrderResponseDTO update(PurchaseOrderUpdateRequestDTO updateRequest) throws ProductNotFoundException, StockInsufficientException;

    PurchaseOrderResponseDTO getById(Long id, String username) throws ProductNotFoundException;

    List<PurchaseOrderResponseDTO> getAll(String username);
}