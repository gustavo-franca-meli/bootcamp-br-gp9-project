package com.mercadolibre.finalProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BatchPurchaseOrderDTO {

    @NotNull
    @JsonProperty("batchPurchaseId")
    private Long id;

    @NotNull
    private Integer quantity;

    @NotNull
    private BatchDTO batchDTO;

    public BatchPurchaseOrderDTO(Long id, Integer quantity, BatchDTO batchDTO) {
        this.id = id;
        this.quantity = quantity;
        this.batchDTO = batchDTO;
    }
}
