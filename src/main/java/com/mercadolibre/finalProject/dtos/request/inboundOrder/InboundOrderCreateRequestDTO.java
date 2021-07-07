package com.mercadolibre.finalProject.dtos.request.inboundOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class InboundOrderCreateRequestDTO {

    @NotNull
    private LocalDate orderDate;
    @NotNull
    private SectorRequestDTO section;
    @NotNull
    private List<BatchRequestCreateDTO> batchStock;

    public InboundOrderCreateRequestDTO(@NotNull LocalDate orderDate, @NotNull SectorRequestDTO section, @NotNull List<BatchRequestCreateDTO> batchStock) {
        this.orderDate = orderDate;
        this.section = section;
        this.batchStock = batchStock;
    }

}
