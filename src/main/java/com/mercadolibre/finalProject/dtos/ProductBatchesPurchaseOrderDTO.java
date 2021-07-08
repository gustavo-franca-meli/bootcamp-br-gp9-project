package com.mercadolibre.finalProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.BatchPurchaseOrder;
import com.mercadolibre.finalProject.model.PurchaseOrder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductBatchesPurchaseOrderDTO {

    @NotNull
    @JsonProperty("purchaseOrderItemId")
    private Long id;

    @NotNull
    private Double currentPricePerUnit;

    @NotNull
    private List<BatchPurchaseOrderDTO> purchaseBatches = new ArrayList<>();

    public ProductBatchesPurchaseOrderDTO(Long id, Double currentPricePerUnit, List<BatchPurchaseOrderDTO> purchaseBatches) {
        this.id = id;
        this.currentPricePerUnit = currentPricePerUnit;
        this.purchaseBatches = purchaseBatches;
    }
}
