package com.mercadolibre.finalProject.dtos.request.inboundOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class InboundOrderUpdateRequestDTO {
    @NotNull
    private Long orderNumber;
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private SectorRequestDTO section;
    @NotNull
    private List<BatchRequestUpdateDTO> batchStock;

    private String username;

    public InboundOrderUpdateRequestDTO(@NotNull Long orderNumber, @NotNull LocalDate orderDate, @NotNull SectorRequestDTO section, @NotNull List<BatchRequestUpdateDTO> batchStock) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.section = section;
        this.batchStock = batchStock;
    }
}
