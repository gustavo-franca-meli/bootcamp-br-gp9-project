package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.SectorDTO;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;

public interface ISectorService {
    Sector findById (Long sectorId) throws Exception;
    Boolean isThereSpace (Batch batch, Long sectorId) throws Exception;
}
