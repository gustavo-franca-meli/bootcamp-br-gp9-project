package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.ProductStockForOrderDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderBatchResponseDTO;
import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.enums.ProductType;

import java.util.List;
import java.util.Set;

public interface ISectorService {
    Boolean isThereSpace(Batch batch, Long sectorId) throws Exception;

    Boolean exist(Long sectorId);

    Sector findById(Long sectorId) throws SectorNotFoundException;

    Integer getProductStockQuantity(ProductStockForOrderDTO productStock);

    List<PurchaseOrderBatchResponseDTO> withdrawStockFromBatches(List<Batch> batches, Integer orderQuantity);

    Boolean hasType(Long sectorID, Set<ProductType> type) throws SectorNotFoundException;
}
