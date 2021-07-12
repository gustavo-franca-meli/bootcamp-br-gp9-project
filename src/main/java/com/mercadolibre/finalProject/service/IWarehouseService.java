package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Warehouse;

public interface IWarehouseService {

    WarehouseResponseDTO findById(Long warehouseCode);

    Warehouse findWarehouseById(Long warehouseCode);
}
