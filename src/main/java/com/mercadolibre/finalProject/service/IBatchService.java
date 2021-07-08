package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.request.SectorBatchRequestDTO;
import com.mercadolibre.finalProject.dtos.response.BatchValidateDateResponseDTO;
import com.mercadolibre.finalProject.dtos.response.SectorBatchResponseDTO;
import com.mercadolibre.finalProject.exceptions.CreateBatchStockException;
import com.mercadolibre.finalProject.model.Batch;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IBatchService {
    BatchDTO withdrawQuantity(Long batchId, Integer withdrawnQuantity);

    SectorBatchResponseDTO getSectorBatchesByProductId(SectorBatchRequestDTO request);

    List<BatchValidateDateResponseDTO> getBatchesBySectorId(Long sectorId, Integer daysQuantity);

    List<BatchValidateDateResponseDTO> getBatchesByProductType(Integer daysQuantity, String category, String direction);

    List<Batch> save(@NotNull List<BatchDTO> batchStock, Long sectorId, Long OrderId) throws CreateBatchStockException;

    void deleteAll(List<Batch> batches);
}
