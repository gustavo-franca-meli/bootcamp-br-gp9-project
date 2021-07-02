package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.model.Sector;

public interface ISectorService {
    Boolean exist(String sectorId);
    Sector findById(String code) throws SectorNotFoundException;
}
