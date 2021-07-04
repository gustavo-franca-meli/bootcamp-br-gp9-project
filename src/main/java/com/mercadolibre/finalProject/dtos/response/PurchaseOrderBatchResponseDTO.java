package com.mercadolibre.finalProject.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseOrderBatchResponseDTO {
    private Long id;
    private Integer quantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;

    public PurchaseOrderBatchResponseDTO(Long id, Integer quantity, LocalDate manufacturingDate, LocalDateTime manufacturingTime, LocalDate dueDate) {
        this.id = id;
        this.quantity = quantity;
        this.manufacturingDate = manufacturingDate;
        this.manufacturingTime = manufacturingTime;
        this.dueDate = dueDate;
    }

    public PurchaseOrderBatchResponseDTO() {
    }
}
