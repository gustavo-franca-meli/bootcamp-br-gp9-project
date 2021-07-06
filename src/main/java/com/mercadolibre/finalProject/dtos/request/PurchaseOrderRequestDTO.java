package com.mercadolibre.finalProject.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderRequestDTO {
    private LocalDate date;
    private Long buyerId;
    private Integer orderStatus;
    private List<ProductPurchaseOrderRequestDTO> products;

    public PurchaseOrderRequestDTO(LocalDate date, Long buyerId, Integer orderStatus, List<ProductPurchaseOrderRequestDTO> products) {
        this.date = date;
        this.buyerId = buyerId;
        this.orderStatus = orderStatus;
        this.products = products;
    }
}
