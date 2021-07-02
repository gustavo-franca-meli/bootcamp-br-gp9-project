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

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public WarehouseResponseDTO findById(Long warehouseCode) throws WarehouseNotFoundException {
        var warehouse = this.findWarehouseBy(warehouseCode);
        return WarehouseMapper.toResponseDTO(warehouse);
    }

    private Warehouse findWarehouseBy(Long id) {
        var data = warehouseRepository.findById(id);
        return data.orElseThrow(() -> new WarehouseNotFoundException());
    }
}
