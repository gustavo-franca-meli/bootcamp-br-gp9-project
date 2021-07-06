package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.SectorResponseDTO;
import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.enums.ProductType;

import java.util.Set;

public interface ISectorService {
    Boolean isThereSpace(Long sectorId);

    Boolean exist(Long sectorId);

    SectorResponseDTO findById(Long sectorId) throws SectorNotFoundException;

    Boolean hasType (Long sectorID, Set<ProductType> productTypes) throws SectorNotFoundException;
}
