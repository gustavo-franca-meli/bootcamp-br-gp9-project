package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.RepresentativeResponseDTO;
import com.mercadolibre.finalProject.exceptions.RepresentativeNotFound;
import com.mercadolibre.finalProject.model.Representative;
import com.mercadolibre.finalProject.model.Warehouse;

public interface IRepresentativeService {

    RepresentativeResponseDTO findById(Long representativeId);

    RepresentativeResponseDTO findByIdAndWarehouseId(Long representativeId, Long warehouseId) throws RepresentativeNotFound;
}
