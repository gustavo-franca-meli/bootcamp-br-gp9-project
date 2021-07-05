package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.ProductDTO;
import com.mercadolibre.finalProject.dtos.ProductStockForOrderDTO;
import com.mercadolibre.finalProject.dtos.PurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderItemResponseDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.model.mapper.WarehouseMapper;
import com.mercadolibre.finalProject.repository.WarehouseRepository;
import com.mercadolibre.finalProject.service.IBatchService;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.ISectorService;
import com.mercadolibre.finalProject.service.IWarehouseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseServiceImpl implements IWarehouseService {

    private WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public WarehouseResponseDTO findById(Long warehouseId) throws WarehouseNotFoundException {
        var warehouse = this.findWarehouseBy(warehouseId);
        return WarehouseMapper.toResponseDTO(warehouse);
    }

    private Warehouse findWarehouseBy(Long warehouseId) {
        var data = warehouseRepository.findById(warehouseId);
        return data.orElseThrow(() -> new WarehouseNotFoundException("Warehouse Not Found. Id:" + warehouseId));
    }

}
