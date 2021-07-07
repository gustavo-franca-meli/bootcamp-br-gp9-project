package com.mercadolibre.finalProject.dtos.request.inboundOrder;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BatchRequestCreateDTO {
    @NotNull
    private Long productId;
    @NotNull
    private Float currentTemperature;
    @NotNull
    private Float minimumTemperature;
    @NotNull
    private Integer initialQuantity;
    @NotNull
    private Integer currentQuantity;
    @NotNull
    private LocalDate manufacturingDate;
    @NotNull
    private LocalDateTime manufacturingTime;
    @NotNull
    private LocalDate dueDate;



    public BatchRequestCreateDTO(Long productId, Float currentTemperature, Float minimumTemperature, Integer initialQuantity, Integer currentQuantity, LocalDate manufacturingDate, LocalDateTime manufacturingTime, LocalDate dueDate) {
        this.productId = productId;
        this.currentTemperature = currentTemperature;
        this.minimumTemperature = minimumTemperature;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.manufacturingDate = manufacturingDate;
        this.manufacturingTime = manufacturingTime;
        this.dueDate = dueDate;
    }

    public BatchRequestCreateDTO(BatchDTO dto) {
        this.productId = dto.getProductId();
        this.currentTemperature = dto.getCurrentTemperature();
        this.minimumTemperature = dto.getMinimumTemperature();
        this.initialQuantity = dto.getInitialQuantity();
        this.currentQuantity = dto.getCurrentQuantity();
        this.manufacturingDate = dto.getManufacturingDate();
        this.manufacturingTime = dto.getManufacturingTime();
        this.dueDate = dto.getDueDate();
    }
}
