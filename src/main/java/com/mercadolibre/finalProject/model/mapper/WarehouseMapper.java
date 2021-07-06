package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.model.Warehouse;

public interface WarehouseMapper {

    static WarehouseResponseDTO toResponseDTO(Warehouse warehouse) {
        var sectors = SectorMapper.toListResponseDTO(warehouse.getSectors());
        var representative = RepresentativeMapper.toResponseDTO(warehouse.getRepresentative());
        return new WarehouseResponseDTO(warehouse.getId(),warehouse.getName(), sectors, representative);
    }

}
