package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;

import java.util.UUID;

public interface ISectorService {
    public Sector findById (UUID sectorId);
    Boolean exist(UUID sectorId);
    Boolean isThereSpace (Batch batch, UUID sectorId) throws Exception;
}
