package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.TransferOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.response.TransferOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.TransferStockException;
import org.springframework.stereotype.Service;


public interface TransferOrderService {

    TransferOrderResponseDTO create(TransferOrderRequestDTO dto, String username) throws TransferStockException;
}
