package com.mercadolibre.finalProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BatchDTO {
    @JsonProperty("batchNumber")
    private UUID id;
    private UUID productId;
    private Float currentTemperature;
    private Float minimumTemperature;
    private Integer initialQuantity;
    private Integer currentQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;

    public BatchDTO() {
    }

    public BatchDTO(UUID id, UUID productId, Float currentTemperature, Float minimumTemperature, Integer initialQuantity, Integer currentQuantity, LocalDate manufacturingDate, LocalDateTime manufacturingTime, LocalDate dueDate) {
        this.id = id;
        this.productId = productId;
        this.currentTemperature = currentTemperature;
        this.minimumTemperature = minimumTemperature;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.manufacturingDate = manufacturingDate;
        this.manufacturingTime = manufacturingTime;
        this.dueDate = dueDate;
    }
}
