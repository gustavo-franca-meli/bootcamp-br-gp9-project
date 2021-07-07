package com.mercadolibre.finalProject.dtos.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SumOfProductStockDTO {
    private Long productId;
    private List<WarehouseProductSumDTO> warehouses;
}
