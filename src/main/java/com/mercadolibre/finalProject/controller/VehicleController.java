package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.response.VehicleResponseDTO;
import com.mercadolibre.finalProject.service.IVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
