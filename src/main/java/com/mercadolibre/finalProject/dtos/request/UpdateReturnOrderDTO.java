package com.mercadolibre.finalProject.dtos.request;

import lombok.Getter;

@Getter
public class UpdateReturnOrderDTO {

    private final Long returnOrderId;
    private final Long warehouseId;
    private final Integer statusCode;
    private String username;

    public UpdateReturnOrderDTO(Long returnOrderId, Long warehouseId, Integer statusCode) {
        this.returnOrderId = returnOrderId;
        this.warehouseId = warehouseId;
        this.statusCode = statusCode;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
