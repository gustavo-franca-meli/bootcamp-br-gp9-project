package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Warehouse;

public interface IWarehouseService {
    Warehouse findById(String warehouseCode) throws WarehouseNotFoundException;
}
