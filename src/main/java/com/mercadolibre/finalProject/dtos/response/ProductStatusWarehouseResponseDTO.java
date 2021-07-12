package com.mercadolibre.finalProject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductStatusWarehouseResponseDTO {
    private Long productId;
    private ProductStatusIntervalDueDateResponseDTO statusDueDateLessThan3Weeks;
    private ProductStatusIntervalDueDateResponseDTO statusDueDateBetweenThan3WeeksAnd2Months;
    private ProductStatusIntervalDueDateResponseDTO statusDueDateMoreThan2Months;
}
