package com.mercadolibre.finalProject.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BatchSectorResponseDTO {

    private Long batchNumber;
    private Long productId;
    private Integer productTypeId;
    private LocalDate dueDate;
    private Integer quantity;

    public BatchSectorResponseDTO(Long batchNumber, Long productId, Integer productTypeId, LocalDate dueDate, Integer quantity) {
        this.batchNumber = batchNumber;
        this.productId = productId;
        this.productTypeId = productTypeId;
        this.dueDate = dueDate;
        this.quantity = quantity;
    }
}
