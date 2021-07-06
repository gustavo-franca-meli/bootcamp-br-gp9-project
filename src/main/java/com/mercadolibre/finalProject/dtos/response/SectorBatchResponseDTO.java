package com.mercadolibre.finalProject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SectorBatchResponseDTO {

    private BatchIdentificationResponseDTO sector;
    private Long productId;
    private List<BatchStockResponseDTO> batchStock = new ArrayList<>();
}
