package com.mercadolibre.finalProject.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductStatusWarehouseDTO {
    private Long productId;
    private Integer countBatchesLess3Weeks;
    private Integer quantityLess3Weeks;
    private Integer countBatchesMore3Weeks;
    private Integer quantityMore3Weeks;
    private Integer countBatchesMore2Months;
    private Integer quantityMore2Months;
}
