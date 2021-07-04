package com.mercadolibre.finalProject.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class PurchaseOrderDTO {
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private Long buyerId;
    @NotNull
    private Integer orderStatus;
    @NotNull
    private List<ProductDTO> products;

    public PurchaseOrderDTO(LocalDate orderDate, Long buyerId, Integer orderStatus, List<ProductDTO> products) {
        this.orderDate = orderDate;
        this.buyerId = buyerId;
        this.orderStatus = orderStatus;
        this.products = products;
    }
}
