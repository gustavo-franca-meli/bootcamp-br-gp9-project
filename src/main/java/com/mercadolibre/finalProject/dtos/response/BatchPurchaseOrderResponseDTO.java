package com.mercadolibre.finalProject.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BatchPurchaseOrderResponseDTO {
    private Long id;
    private Long batchId;
    private Integer quantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;

    public BatchPurchaseOrderResponseDTO(Long id, Integer quantity, LocalDate manufacturingDate, LocalDateTime manufacturingTime, LocalDate dueDate) {
        this.id = id;
        this.quantity = quantity;
        this.manufacturingDate = manufacturingDate;
        this.manufacturingTime = manufacturingTime;
        this.dueDate = dueDate;
    }

    public BatchPurchaseOrderResponseDTO(Long id, Long batchId, Integer quantity, LocalDate manufacturingDate, LocalDateTime manufacturingTime, LocalDate dueDate) {
        this.id = id;
        this.batchId = batchId;
        this.quantity = quantity;
        this.manufacturingDate = manufacturingDate;
        this.manufacturingTime = manufacturingTime;
        this.dueDate = dueDate;
    }

    public BatchPurchaseOrderResponseDTO() {
    }
}
