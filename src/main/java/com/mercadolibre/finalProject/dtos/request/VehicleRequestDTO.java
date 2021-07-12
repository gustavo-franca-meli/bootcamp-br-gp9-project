package com.mercadolibre.finalProject.dtos.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VehicleRequestDTO {

    @NotNull
    private Integer vehicleType;

    @NotNull
    private Integer capacity;

    @NotNull
    private String plate;

    private Long warehouseId;

}
