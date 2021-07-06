package com.mercadolibre.finalProject.dtos.request.inboundOrder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BatchRequestUpdateDTO extends BatchRequestCreateDTO {
    @JsonProperty("batchNumber")
    @NotNull
    private Long id;


    public BatchRequestUpdateDTO(@NotNull Long id, Long productId, Float currentTemperature, Float minimumTemperature, Integer initialQuantity, Integer currentQuantity, LocalDate manufacturingDate, LocalDateTime manufacturingTime, LocalDate dueDate) {
        super(productId, currentTemperature, minimumTemperature, initialQuantity, currentQuantity, manufacturingDate, manufacturingTime, dueDate);
        this.id = id;
    }
}
