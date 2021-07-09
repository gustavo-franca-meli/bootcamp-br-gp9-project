package com.mercadolibre.finalProject.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseProductSumDTO {

    private Long warehouseCode;

    private Integer totalQuantity;
}
