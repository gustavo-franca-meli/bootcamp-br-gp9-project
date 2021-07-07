package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.SectorResponseDTO;
import com.mercadolibre.finalProject.model.Batch;

public interface ISectorService {
    Boolean isThereSpace(Long sectorId);

    Boolean exist(Long sectorId);

    SectorResponseDTO findById(Long sectorId);

    Boolean hasType(Long sectorID, Integer productType);
}
