package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.request.SectorBatchRequestDTO;
import com.mercadolibre.finalProject.dtos.response.SectorBatchResponseDTO;
import com.mercadolibre.finalProject.exceptions.CreateBatchStockException;
import com.mercadolibre.finalProject.model.Batch;

import java.util.List;

public interface IBatchService {
    List<Batch> create(List<BatchDTO> batchStock, Long sectorId, Long orderId) throws CreateBatchStockException;

    BatchDTO withdrawQuantity(Long batchId, Integer withdrawnQuantity);

    SectorBatchResponseDTO getSectorBatchesByProductId(SectorBatchRequestDTO request);

}
