package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.enums.SectorType;

public interface ISectorService {
    Boolean isThereSpace (Batch batch) throws Exception;
    Boolean exist(Long sectorId);
    Sector findById(Long code) throws SectorNotFoundException;
    Boolean hasType(Long sectorID, SectorType type) throws SectorNotFoundException;
}
