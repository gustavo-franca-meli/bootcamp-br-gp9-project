package com.mercadolibre.finalProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class PurchaseOrderDTO {
    @NotNull
    @JsonProperty("purchaseOrderId")
    private Long buyerId;

    @NotNull
    private LocalDate orderDate;

    @NotNull
    private Integer orderStatus;

    @NotNull
    private List<ProductBatchesPurchaseOrderDTO> products;

    public PurchaseOrderDTO(Long buyerId, LocalDate orderDate, Integer orderStatus, List<ProductBatchesPurchaseOrderDTO> products) {
        this.buyerId = buyerId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.products = products;
    }
}
