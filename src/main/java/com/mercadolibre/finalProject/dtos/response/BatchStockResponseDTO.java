package com.mercadolibre.finalProject.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BatchStockResponseDTO {

    private Long batchNumber;
    private Integer currentQuantity;
    private LocalDate dueDate;
}
