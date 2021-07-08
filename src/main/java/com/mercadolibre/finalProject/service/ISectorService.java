package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.SectorResponseDTO;

public interface ISectorService {
    Boolean isThereSpace(Long sectorId);

    Boolean exist(Long sectorId);

    SectorResponseDTO findById(Long sectorId);

    Boolean hasType(Long sectorID, Integer productType);

    void isThereSpaceFor(Integer quantityBatches, Long sectorId);

}