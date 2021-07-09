package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.response.SectorResponseDTO;
import com.mercadolibre.finalProject.exceptions.NoSpaceInSectorException;
import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
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
        var sector = this.findSectorById(sectorId);

        return SectorMapper.toResponseDTO(sector);
    }

    private Sector findSectorById(Long sectorId) {
        var sector = this.sectorRepository.findById(sectorId);

        return sector.orElseThrow(() -> new SectorNotFoundException("Sector " + sectorId +" Not Found"));
    }

    @Override
    public Boolean hasType(Long sectorId, Integer productType) throws SectorNotFoundException {
        var sector = this.findSectorById(sectorId);

        return sector.getSectorType() == productType;
    }

    @Override
    public Boolean exist(Long sectorId) {
        return sectorRepository.findById(sectorId).isPresent();
    }

    @Override
    public Boolean isThereSpace(Long sectorId) {
        var sector = this.findSectorById(sectorId);
        var totalQuantity = this.getQuantityBatchesValidInSector(sector.getId());

        if (totalQuantity >= sector.getMaxQuantityBatches())
            throw new NoSpaceInSectorException("Sector " + sectorId + " doesn't have enough space");
        return true;
    }

    private Integer getQuantityBatchesValidInSector(Long sectorId) {
        var quantity = this.sectorRepository.countBatchesIn(sectorId);
        if (quantity == null)
            return 0;
        return quantity;
    }

    @Override
    public void isThereSpaceFor(Integer quantityBatches, Long sectorId) {
        var sector = this.findSectorById(sectorId);
        var quantityInStock = this.getQuantityBatchesValidInSector(sector.getId());
        var totalStock = quantityInStock + quantityBatches;

        if (totalStock >= sector.getMaxQuantityBatches())
            throw new NoSpaceInSectorException("Sector " + sectorId + " doesn't have enough space to new quantity. New quantity " + quantityBatches);
    }
}
