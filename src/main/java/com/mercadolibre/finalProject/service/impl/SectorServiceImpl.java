package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.ProductStockForOrderDTO;
import com.mercadolibre.finalProject.dtos.SectorDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderBatchResponseDTO;
import com.mercadolibre.finalProject.exceptions.NoSpaceInSectorException;
import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.enums.ProductType;
import com.mercadolibre.finalProject.repository.SectorRepository;
import com.mercadolibre.finalProject.service.IBatchService;
import com.mercadolibre.finalProject.service.ISectorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SectorServiceImpl implements ISectorService {

    private SectorRepository sectorRepository;
    private IBatchService batchService;

    public SectorServiceImpl(SectorRepository sectorRepository, IBatchService batchService) {
        this.sectorRepository = sectorRepository;
        this.batchService = batchService;
    }

//    public SectorDTO getById (Sector sector) { return new SectorDTO(sector); }

    @Override
    public Sector findById (Long sectorId) throws SectorNotFoundException {
        Optional<Sector> sectorOpt = this.sectorRepository.findById(sectorId);

        if(sectorOpt.isEmpty()) { throw new SectorNotFoundException(); }

        return sectorOpt.get();
    }


    public Integer getProductStockQuantity (ProductStockForOrderDTO productStock) {
        return productStock.getBatches().stream().mapToInt(Batch::getCurrentQuantity).sum();
    }

    @Override
    public List<PurchaseOrderBatchResponseDTO> withdrawStockFromBatches(List<Batch> batches, Integer orderQuantity) {
        List<PurchaseOrderBatchResponseDTO> purchaseBatches = new ArrayList<>();
        Integer withdrawnQuantity = 0;

        for(Batch batch : batches) {
            if(withdrawnQuantity >= orderQuantity) {
                break;
            }
            PurchaseOrderBatchResponseDTO purchaseBatch = this.batchService.withdrawStockFromBatch(batch,withdrawnQuantity,orderQuantity);
            withdrawnQuantity += purchaseBatch.getQuantity();
            purchaseBatches.add(purchaseBatch);
        }
        return purchaseBatches;
    }

    @Override
    public Boolean hasType(Long sectorID, ProductType type) throws SectorNotFoundException {
        var sector = findById(sectorID);
        var types = sector.getTypes();
        return types.stream().anyMatch((t)-> ProductType.toEnum(t).equals(type) );
    }


    @Override
    public Boolean exist (Long sectorId) {
        return sectorRepository.findById(sectorId).isPresent();
    }

    @Override
    public Boolean isThereSpace (Batch batch, Long sectorId) throws Exception{
        // checks whether there's enough space for batch in the sector

//        Sector sector = this.findById(sectorId);
//        if( (sector.getCurrentQuantityBatches() + batch.getCurrentQuantity()) > sector.getMaxQuantityBatches() ) {
//            throw new NoSpaceInSectorException("Sector " + sectorId + " doesn't have enough space for batch " + batch.getId());
//        }

        return true;
    }
}
