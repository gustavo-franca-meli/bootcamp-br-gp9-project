package com.mercadolibre.finalProject.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class InboundOrderDTO {
    @NotNull
    private Double orderNumber;
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private SectionDto section;
    @NotNull
    private List<BatchDto> batchStock;
}