package com.mercadolibre.finalProject.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateReturnOrderRequestDTO {

    private Long returnOrderId;
    private Long warehouseId;
    private Integer statusCode;
    private String username;

    public UpdateReturnOrderRequestDTO(Long returnOrderId, Long warehouseId, Integer statusCode) {
        this.returnOrderId = returnOrderId;
        this.warehouseId = warehouseId;
        this.statusCode = statusCode;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
