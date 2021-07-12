package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.model.mapper.WarehouseMapper;
import com.mercadolibre.finalProject.repository.WarehouseRepository;
import com.mercadolibre.finalProject.service.IWarehouseService;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl implements IWarehouseService {

    private WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public WarehouseResponseDTO findById(Long warehouseId) {
        var warehouse = this.findWarehouseById(warehouseId);
        return WarehouseMapper.toResponseDTO(warehouse);
    }

    @Override
    public Warehouse findWarehouseById(Long warehouseId) {
        var data = warehouseRepository.findById(warehouseId);
        return data.orElseThrow(() -> new WarehouseNotFoundException("Warehouse Not Found. Id:" + warehouseId));
    }
}
