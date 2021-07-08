package com.mercadolibre.finalProject.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BatchValidateDateResponseDTO {

    private Long batchNumber;
    private Long productId;
    private Integer productTypeId;
    private LocalDate dueDate;
    private Integer quantity;

    public BatchValidateDateResponseDTO(Long batchNumber, Long productId, Integer productTypeId, LocalDate dueDate, Integer quantity) {
        this.batchNumber = batchNumber;
        this.productId = productId;
        this.productTypeId = productTypeId;
        this.dueDate = dueDate;
        this.quantity = quantity;
    }
}
