package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.ProductBatchesPurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.enums.ProductType;

import java.util.List;

public interface ISectorService {
    Boolean isThereSpace (Batch batch, Long sectorId) throws Exception;
    Boolean exist (Long sectorId);
    Sector findById (Long sectorId) throws SectorNotFoundException;
    Integer getProductStockQuantity (ProductBatchesPurchaseOrderDTO productStock);
    List<BatchPurchaseOrderResponseDTO> withdrawStockFromBatches (List<Batch> batches, Integer orderQuantity);
    Boolean hasType(Long sectorID, ProductType type) throws SectorNotFoundException;
}
