package com.mercadolibre.finalProject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorBatchResponseDTO {

    private BatchIdentificationResponseDTO sector;
    private Long productId;
    private List<BatchStockResponseDTO> batchStock = new ArrayList<>();
}
