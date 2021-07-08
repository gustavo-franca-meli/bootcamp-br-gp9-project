package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.model.Warehouse;

public interface WarehouseMapper {

    static WarehouseResponseDTO toResponseDTO(Warehouse warehouse) {
        var sectors = SectorMapper.toListResponseDTO(warehouse.getSectors());
        return new WarehouseResponseDTO(warehouse.getId(),warehouse.getName(), sectors);
    }

}
