package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.model.enums.VehicleType;
import lombok.Data;

@Data
public class VehicleResponseDTO {

    private Long id;

    private VehicleType vehicleType;

    private Integer capacity;

    private String plate;

    private Boolean isAvailable;

    private Long warehouse_id;

    public VehicleResponseDTO(Long id, VehicleType vehicleType, Integer capacity, String plate, Boolean isAvailable, Long warehouse_id) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.plate = plate;
        this.isAvailable = isAvailable;
        this.warehouse_id = warehouse_id;
    }
}
