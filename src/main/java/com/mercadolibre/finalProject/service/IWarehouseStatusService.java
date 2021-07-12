package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.ProductStatusWarehouseResponseDTO;

import java.util.List;

public interface IWarehouseStatusService {
    List<ProductStatusWarehouseResponseDTO> getWarehouseStatus(String representativeUsername);
}
