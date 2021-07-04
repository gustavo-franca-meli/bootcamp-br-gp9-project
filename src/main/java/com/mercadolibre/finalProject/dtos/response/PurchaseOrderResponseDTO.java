package com.mercadolibre.finalProject.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderResponseDTO {
    private LocalDate date;
    private double totalPrice;
    private List<PurchaseOrderItemResponseDTO> items;

    public PurchaseOrderResponseDTO(LocalDate date, double totalPrice, List<PurchaseOrderItemResponseDTO> items) {
        this.date = date;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public PurchaseOrderResponseDTO(double totalPrice, List<PurchaseOrderItemResponseDTO> items) {
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public PurchaseOrderResponseDTO() {
    }
}
