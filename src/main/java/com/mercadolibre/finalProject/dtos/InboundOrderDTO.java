package com.mercadolibre.finalProject.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import com.mercadolibre.finalProject.dtos.BatchDTO;

@Data
public class InboundOrderDTO {
    @NotNull
    private Double orderNumber;
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private SectorDTO section;
    @NotNull
    private List<BatchDTO> batchStock;
}