package com.mercadolibre.finalProject.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import com.mercadolibre.finalProject.dtos.BatchDTO;

@Data
public class InboundOrderDTO {
    @NotNull
    private Integer orderNumber;
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private SectorDTO section;
    @NotNull
    private List<BatchDTO> batchStock;

    public InboundOrderDTO(@NotNull Integer orderNumber, @NotNull LocalDate orderDate, @NotNull SectionDto section, @NotNull List<BatchDTO> batchStock) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.section = section;
        this.batchStock = batchStock;
    }
}