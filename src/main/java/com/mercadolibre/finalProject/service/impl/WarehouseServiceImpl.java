package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.exceptions.NotFoundException;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.model.mapper.WarehouseMapper;
import com.mercadolibre.finalProject.repository.WarehouseRepository;
import com.mercadolibre.finalProject.service.IWarehouseService;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl implements IWarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public WarehouseResponseDTO findById(String warehouseCode) throws WarehouseNotFoundException {
        var warehouse = this.findWarehouseBy(Long.valueOf(warehouseCode));
        return WarehouseMapper.toResponseDTO(warehouse);
    }

    private Warehouse findWarehouseBy(Long id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException("This warehouse was not found. Id: " + id));
    }
}
