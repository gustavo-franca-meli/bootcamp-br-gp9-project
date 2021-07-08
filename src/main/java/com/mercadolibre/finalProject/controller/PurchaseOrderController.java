package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.service.IPurchaseOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/purchase-order")
public class PurchaseOrderController {

    IPurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(IPurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderResponseDTO> create(@RequestBody PurchaseOrderRequestDTO dto) throws Exception {
        PurchaseOrderResponseDTO response = purchaseOrderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponseDTO> getById (@PathVariable Long id) throws Exception {
        PurchaseOrderResponseDTO response = purchaseOrderService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<PurchaseOrderResponseDTO> update(@RequestBody PurchaseOrderUpdateRequestDTO dto) throws Exception {
        PurchaseOrderResponseDTO response = purchaseOrderService.update(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
