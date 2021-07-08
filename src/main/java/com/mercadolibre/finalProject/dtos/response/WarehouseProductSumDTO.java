package com.mercadolibre.finalProject.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseProductSumDTO {
    @JsonProperty("warehouseCode")
    private Long warehouseCode;
    @JsonProperty("totalQuantity")
    private Integer totalQuantity;


}
