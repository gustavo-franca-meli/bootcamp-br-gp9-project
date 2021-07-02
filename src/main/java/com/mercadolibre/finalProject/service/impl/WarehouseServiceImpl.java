package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.service.IWarehouseService;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl implements IWarehouseService {
    @Override
    public Warehouse findById(Long warehouseCode) throws WarehouseNotFoundException {
        return null;
    }
}
