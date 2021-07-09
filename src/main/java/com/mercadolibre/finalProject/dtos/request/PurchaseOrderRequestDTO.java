package com.mercadolibre.finalProject.dtos.request;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderRequestDTO {
    private LocalDate date;
    private Long buyerId;
    private Integer orderStatus;
    private List<ProductPurchaseOrderRequestDTO> products;
}
