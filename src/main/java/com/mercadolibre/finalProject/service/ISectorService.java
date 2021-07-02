package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;

public interface ISectorService {
    Boolean isThereSpace (Batch batch, Long sectorId) throws Exception;
    Boolean exist(Long sectorId);
    Sector findById(Long sectorId) throws SectorNotFoundException;
}
