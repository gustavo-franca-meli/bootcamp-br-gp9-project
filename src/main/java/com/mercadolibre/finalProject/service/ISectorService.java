package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;

public interface ISectorService {
<<<<<<< feature/service-sector
    Sector findById (Long sectorId);
    Boolean exist (Long sectorId);
=======
>>>>>>> feature/US001
    Boolean isThereSpace (Batch batch, Long sectorId) throws Exception;
    Boolean exist(Long sectorId);
    Sector findById(Long code) throws SectorNotFoundException;
}
