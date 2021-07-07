package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.BatchDTO;

import com.mercadolibre.finalProject.exceptions.CreateBatchStockException;
import com.mercadolibre.finalProject.model.Batch;


import javax.validation.constraints.NotNull;
import java.util.List;


public interface IBatchService {
    List<Batch> save(@NotNull  List<BatchDTO> batchStock, Long sectorId,Long OrderId) throws CreateBatchStockException;
    BatchDTO withdrawQuantity (Long batchId, Integer withdrawnQuantity);

    void deleteAll(List<Batch> batches);
}
