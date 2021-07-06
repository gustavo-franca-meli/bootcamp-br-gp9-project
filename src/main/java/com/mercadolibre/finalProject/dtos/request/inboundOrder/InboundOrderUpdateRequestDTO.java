package com.mercadolibre.finalProject.dtos.request.inboundOrder;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class InboundOrderUpdateRequestDTO {
    @NotNull
    private Integer orderNumber;
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private SectorRequestDTO section;
    @NotNull
    private List<BatchRequestUpdateDTO> batchStock;

    public InboundOrderUpdateRequestDTO(@NotNull Integer orderNumber, @NotNull LocalDate orderDate, @NotNull SectorRequestDTO section, @NotNull List<BatchRequestUpdateDTO> batchStock) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.section = section;
        this.batchStock = batchStock;
    }
}
