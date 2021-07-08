package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.RepresentativeResponseDTO;

public interface IRepresentativeService {

    RepresentativeResponseDTO findById(Long representativeId);

    RepresentativeResponseDTO findByIdAndWarehouseId(Long representativeId, Long warehouseId);

    RepresentativeResponseDTO findByAccountUsername(String username);

    RepresentativeResponseDTO findByAccountUsernameAndWarehouseId(String username, Long warehouseId);
}
