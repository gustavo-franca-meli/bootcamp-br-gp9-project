package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.SectorDTO;
import com.mercadolibre.finalProject.exceptions.NoSpaceInSectorException;
import com.mercadolibre.finalProject.exceptions.SectorNotFound;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.repository.SectorRepository;
import com.mercadolibre.finalProject.service.ISectorService;

import java.util.Optional;


public class SectorServiceImpl implements ISectorService {

    private SectorRepository sectorRepository;

    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    public SectorDTO getById (Sector sector) { return new SectorDTO(sector); }

    @Override
    public Sector findById (Long sectorId) {
        Optional<Sector> sectorOpt = this.sectorRepository.findById(sectorId);

        if(sectorOpt.isEmpty()) { throw new SectorNotFound(); }

        return sectorOpt.get();
    }

    @Override
    public Boolean exist(Long sectorId) {
        return sectorRepository.findById(sectorId).isPresent();
    }

    @Override
    public Boolean isThereSpace(Batch batch, Long sectorId) throws Exception{
        // checks whether there's enough space for batch in the sector

        Sector sector = findById(sectorId);
        if( (sector.getCurrentQuantityBatches() + batch.getCurrentQuantity()) > sector.getMaxQuantityBatches() ) {
            throw new NoSpaceInSectorException("Sector " + sectorId + " doesn't have enough space for batch " + batch.getId());
        }

        return true;
    }
}
