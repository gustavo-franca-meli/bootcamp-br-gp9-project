package com.mercadolibre.finalProject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SumOfProductStockDTO {
    private Long productId;
    private List<WarehouseProductSumDTO> warehouses;
}