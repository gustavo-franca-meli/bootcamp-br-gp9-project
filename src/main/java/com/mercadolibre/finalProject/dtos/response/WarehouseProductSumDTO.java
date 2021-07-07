package com.mercadolibre.finalProject.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class WarehouseProductSumDTO {
    @JsonProperty("warehouseCode")
    private Long warehouseCode;
    @JsonProperty("totalQuantity")
    private Integer totalQuantity;
}
