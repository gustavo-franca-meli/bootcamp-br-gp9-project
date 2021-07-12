package com.mercadolibre.finalProject.dtos.request;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.BatchRequestCreateDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.SectorRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class TransferOrderRequestDTO {
    @NotNull
    private Long warehouseId;
    @NotNull
    private Long toWarehouseId;
    @NotNull
    private List<BatchId> batchStock;

    public TransferOrderRequestDTO(@NotNull Long warehouseId, @NotNull Long toWarehouseId, @NotNull List<BatchId> batchStock) {
        this.warehouseId = warehouseId;
        this.toWarehouseId = toWarehouseId;
        this.batchStock = batchStock;
    }
}

