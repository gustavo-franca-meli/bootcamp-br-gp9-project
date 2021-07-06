package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.BatchPurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.PurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.CreateBatchStockException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IBatchService {
    List<Batch> create(@NotNull  List<BatchDTO> batchStock, Sector sector) throws CreateBatchStockException;
//    BatchPurchaseOrderResponseDTO withdrawStockFromBatch (Batch batch, Integer withdrawnQuantity, Integer orderQuantity);
    Boolean isThereStockForPurchaseOrder(PurchaseOrderRequestDTO purchaseOrder, Long id);
    BatchDTO withdrawQuantity (Long batchId, Integer withdrawnQuantity);
}
