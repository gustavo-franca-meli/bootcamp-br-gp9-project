package com.mercadolibre.finalProject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductWarningStatusWarehouseResponseDTO {
    private Long productId;
    private ProductStatusIntervalDueDateResponseDTO statusDueDateLessThan3Weeks;
}
