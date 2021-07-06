package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.PurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.service.IPurchaseOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-products/purchase-order")
public class PurchaseOrderController {

    IPurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(IPurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderResponseDTO> create(@Valid @RequestBody PurchaseOrderRequestDTO dto, String token) throws Exception {
        PurchaseOrderResponseDTO response = purchaseOrderService.create(dto,token);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PurchaseOrderResponseDTO> getById (@PathVariable(required = true) Long id, String token) throws Exception {
        PurchaseOrderResponseDTO response = purchaseOrderService.getById(id,token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
