package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;

public interface IWarehouseService {
    WarehouseResponseDTO findById(String warehouseCode) throws WarehouseNotFoundException;
}
