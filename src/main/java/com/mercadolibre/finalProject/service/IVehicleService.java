package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.request.VehicleRequestDTO;
import com.mercadolibre.finalProject.dtos.response.VehicleResponseDTO;
import com.mercadolibre.finalProject.service.crud.ICRUD;

import java.util.List;

public interface IVehicleService extends ICRUD<VehicleRequestDTO, VehicleResponseDTO> {

    public VehicleResponseDTO findVehicleByPlate(String plate);

    public List<VehicleResponseDTO> findVehiclesByWarehouseId(Long warehouseId);

}
