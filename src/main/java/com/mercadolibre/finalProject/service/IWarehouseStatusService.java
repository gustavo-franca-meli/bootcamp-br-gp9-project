package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.ProductStatusWarehouseResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductWarningStatusWarehouseResponseDTO;

import java.util.List;

public interface IWarehouseStatusService {
    List<ProductStatusWarehouseResponseDTO> getWarehouseStatus(String username);
    List<ProductWarningStatusWarehouseResponseDTO> getWarehouseWarningProducts (String username);
}
