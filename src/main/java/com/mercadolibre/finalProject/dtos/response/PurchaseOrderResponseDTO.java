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
    private List<ProductBatchesPurchaseOrderResponseDTO> products;

    public PurchaseOrderResponseDTO(LocalDate date, double totalPrice, List<ProductBatchesPurchaseOrderResponseDTO> products) {
        this.date = date;
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public PurchaseOrderResponseDTO(double totalPrice, List<ProductBatchesPurchaseOrderResponseDTO> products) {
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public PurchaseOrderResponseDTO() {
    }
}
