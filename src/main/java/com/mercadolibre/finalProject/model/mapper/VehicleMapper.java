package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.*;
import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.enums.VehicleType;

import java.util.List;
import java.util.stream.Collectors;

public interface VehicleMapper {

    static VehicleResponseDTO toResponseDTO(Vehicle vehicle) {
        return new VehicleResponseDTO(
                vehicle.getId(),
                VehicleType.toEnum(vehicle.getVehicleType()),
                vehicle.getCapacity(),
                vehicle.getPlate(),
                vehicle.getIsAvailable(),
                vehicle.getWarehouse().getId());
    }

    static List<VehicleResponseDTO> toListResponseDTO(List<Vehicle> vehicles) {
        return vehicles.stream().map(VehicleMapper::toResponseDTO).collect(Collectors.toList());
    }

}
