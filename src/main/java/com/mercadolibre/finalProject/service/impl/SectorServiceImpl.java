package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.response.SectorResponseDTO;
import com.mercadolibre.finalProject.exceptions.NoSpaceInSectorException;
import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.mapper.SectorMapper;
import com.mercadolibre.finalProject.repository.SectorRepository;
import com.mercadolibre.finalProject.service.ISectorService;
import org.springframework.stereotype.Service;

@Service
public class SectorServiceImpl implements ISectorService {

    private final SectorRepository sectorRepository;

    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @Override
    public SectorResponseDTO findById(Long sectorId) throws SectorNotFoundException {
        var sector = this.findSectorBy(sectorId);

        return SectorMapper.toResponseDTO(sector);
    }

    private Sector findSectorBy(Long sectorId) {
        var sector = this.sectorRepository.findById(sectorId);
        return sector.orElseThrow();
    }

    @Override
    public Boolean hasType(Long sectorId, Integer productType) throws SectorNotFoundException {
        var sector = this.findSectorBy(sectorId);

        return sector.getSectorType() == productType;
    }

    @Override
    public Boolean exist(Long sectorId) {
        return sectorRepository.findById(sectorId).isPresent();
    }

    @Override
    public Boolean isThereSpace(Batch batch, Long sectorId) {
        var sector = this.findSectorBy(sectorId);
        var totalQuantity = sector.getBatches().size() + batch.getInitialQuantity();

        if (totalQuantity > sector.getMaxQuantityBatches()) {
            throw new NoSpaceInSectorException("Sector " + sectorId + " doesn't have enough space for batch " + batch.getId());
        }

        return true;
    }
}
