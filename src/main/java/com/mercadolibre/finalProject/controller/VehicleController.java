package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.request.VehicleRequestDTO;
import com.mercadolibre.finalProject.dtos.response.VehicleResponseDTO;
import com.mercadolibre.finalProject.service.IVehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(path = "/api/v1/vehicles")
@RestController
public class VehicleController {

    IVehicleService vehicleService;

    public VehicleController(IVehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> findAllVehicles() {
        var vehicles = vehicleService.findAll();

        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleResponseDTO> findVehicleById(@PathVariable Long vehicleId) {
        var vehicle = vehicleService.findById(vehicleId);

        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/plate")
    public ResponseEntity<VehicleResponseDTO> getVehicleByPlate(@RequestParam(required = true) String value) {
        var vehicle = vehicleService.findVehicleByPlate(value);

        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/warehouse")
    public ResponseEntity<List<VehicleResponseDTO>> getVehiclesByWarehouseId(@RequestParam(required = true) Long id) {
        var vehicles = vehicleService.findVehiclesByWarehouseId(id);

        return ResponseEntity.ok(vehicles);
    }
}
