package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.ProductStatusWarehouseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductStatusWarehouseResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductWarningStatusWarehouseResponseDTO;
import com.mercadolibre.finalProject.service.IWarehouseStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/warehouse-status")
public class WarehouseStatusController {

    private IWarehouseStatusService service;

    public WarehouseStatusController(IWarehouseStatusService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductStatusWarehouseResponseDTO>> getWarehouseProductsStatuses () {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        List<ProductStatusWarehouseResponseDTO> response = this.service.getWarehouseStatus(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/warning")
    public ResponseEntity<List<ProductWarningStatusWarehouseResponseDTO>> getWarehouseWarningProductsStatuses () {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        List<ProductWarningStatusWarehouseResponseDTO> response = this.service.getWarehouseWarningProducts(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
