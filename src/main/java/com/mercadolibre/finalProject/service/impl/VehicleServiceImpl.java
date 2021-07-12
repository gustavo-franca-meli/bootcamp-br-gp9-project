package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.request.VehicleRequestDTO;
import com.mercadolibre.finalProject.dtos.response.VehicleResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;
import com.mercadolibre.finalProject.model.Vehicle;
import com.mercadolibre.finalProject.model.mapper.VehicleMapper;
import com.mercadolibre.finalProject.repository.VehicleRepository;
import com.mercadolibre.finalProject.service.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements IVehicleService {

    VehicleRepository vehicleRepository;
    IWarehouseService warehouseService;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, IWarehouseService warehouseService) {
        this.vehicleRepository = vehicleRepository;
        this.warehouseService = warehouseService;
    }

    @Override
    public VehicleResponseDTO create(VehicleRequestDTO vehicleRequestDTO) {
        var vehicleOpt = vehicleRepository.getVehicleByPlate(vehicleRequestDTO.getPlate());

        if (vehicleOpt.isPresent()) throw new VehicleAlreadyExistsException(vehicleRequestDTO.getPlate());

        var warehouse = warehouseService.findWarehouseById(vehicleRequestDTO.getWarehouse_id());

        var vehicle = new Vehicle(
                vehicleRequestDTO.getVehicleType(),
                vehicleRequestDTO.getCapacity(),
                vehicleRequestDTO.getPlate(),
                warehouse);

        vehicle = vehicleRepository.save(vehicle);

        return VehicleMapper.toResponseDTO(vehicle);
    }

    @Override
    public VehicleResponseDTO update(Long id, VehicleRequestDTO vehicleRequestDTO) {
        var vehicle = getVehicleById(id);

        vehicle.setVehicleType(vehicleRequestDTO.getVehicleType() != null ? vehicleRequestDTO.getVehicleType() : vehicle.getVehicleType());
        vehicle.setCapacity(vehicleRequestDTO.getCapacity() != null ? vehicleRequestDTO.getCapacity() : vehicle.getCapacity());
        vehicle.setPlate(vehicleRequestDTO.getPlate() != null ? vehicleRequestDTO.getPlate() : vehicle.getPlate());

        if (vehicleRequestDTO.getWarehouse_id() != null) {
            var warehouse = warehouseService.findWarehouseById(vehicleRequestDTO.getWarehouse_id());
            vehicle.setWarehouse(warehouse);
        }

        vehicle = vehicleRepository.save(vehicle);

        return VehicleMapper.toResponseDTO(vehicle);
    }

    @Override
    public void delete(Long id) {
        var vehicle = getVehicleById(id);

        vehicleRepository.delete(vehicle);
    }

    @Override
    public VehicleResponseDTO findById(Long id) {
        var vehicle = getVehicleById(id);

        return VehicleMapper.toResponseDTO(vehicle);
    }

    @Override
    public List<VehicleResponseDTO> findAll() {
        var vehicles = vehicleRepository.findAll();

        if (vehicles.isEmpty()) throw new NotFoundException("List is empty");

        return VehicleMapper.toListResponseDTO(vehicles);
    }

    @Override
    public VehicleResponseDTO findVehicleByPlate(String plate) {
        var vehicle = vehicleRepository.getVehicleByPlate(plate);

        if (vehicle.isEmpty()) throw new VehicleNotFoundException(plate);

        return VehicleMapper.toResponseDTO(vehicle.get());
    }

    @Override
    public List<VehicleResponseDTO> findVehiclesByWarehouseId(Long warehouseId) {
        warehouseService.findWarehouseById(warehouseId);

        var vehicles = vehicleRepository.getVehiclesByWarehouseId(warehouseId);

        if (vehicles.isEmpty()) throw new NotFoundException("List is empty");

        return VehicleMapper.toListResponseDTO(vehicles);
    }

    private Vehicle getVehicleById(Long id) {
        var vehicleOpt = vehicleRepository.findById(id);

        if (vehicleOpt.isEmpty()) throw new VehicleNotFoundException(id);

        return vehicleOpt.get();
    }
}
