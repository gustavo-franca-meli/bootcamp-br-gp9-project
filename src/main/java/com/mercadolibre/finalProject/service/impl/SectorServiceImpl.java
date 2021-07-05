package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.ProductStockForOrderDTO;
import com.mercadolibre.finalProject.dtos.response.SectorResponseDTO;
import com.mercadolibre.finalProject.exceptions.NoSpaceInSectorException;
import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.enums.ProductType;
import com.mercadolibre.finalProject.model.mapper.SectorMapper;
import com.mercadolibre.finalProject.repository.SectorRepository;
import com.mercadolibre.finalProject.service.ISectorService;
import org.springframework.stereotype.Service;

import java.util.Set;

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

        return sector.orElseThrow(() -> new SectorNotFoundException("Sector Not Found. Id:" + sectorId));
    }

//    @Override
//    public List<PurchaseOrderBatchResponseDTO> withdrawStockFromBatches(List<Batch> batches, Integer orderQuantity) {
//        List<PurchaseOrderBatchResponseDTO> purchaseBatches = new ArrayList<>();
//        Integer withdrawnQuantity = 0;
//
//        for (Batch batch : batches) {
//            var purchaseBatch = purchaseBatchAndWithdrawStock(orderQuantity, withdrawnQuantity, batch);
//            withdrawnQuantity += purchaseBatch.getQuantity();
//            purchaseBatches.add(purchaseBatch);
//
//            if (withdrawnQuantity >= orderQuantity)
//                break;
//        }
//        return purchaseBatches;
//    }
//
//    private PurchaseOrderBatchResponseDTO purchaseBatchAndWithdrawStock(Integer orderQuantity, Integer withdrawnQuantity, Batch batch) {
//        return this.batchService.withdrawStockFromBatch(batch, withdrawnQuantity, orderQuantity);
//    }

    public Integer getProductStockQuantity(ProductStockForOrderDTO productStock) {
        return productStock.getBatches().stream().mapToInt(Batch::getCurrentQuantity).sum();
    }

    @Override
    public Boolean hasType(Long sectorID, Set<ProductType> productTypes) throws SectorNotFoundException {
        var sector = this.findSectorBy(sectorID);
        var sectorTypes = sector.getTypesInProductType();
        return productTypes.stream().anyMatch(p -> sectorTypes.stream().anyMatch((s -> s .getCod() == p.getCod())));
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
