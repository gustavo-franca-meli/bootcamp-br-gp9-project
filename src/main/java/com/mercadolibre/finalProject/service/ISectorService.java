package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;

public interface ISectorService {
    public Sector findById (Long sectorId);
    Boolean exist(Long sectorId);
    Boolean isThereSpace (Batch batch, Long sectorId) throws Exception;
}
