package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.TransferOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.response.TransferOrderResponseDTO;
import com.mercadolibre.finalProject.service.TransferOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products/transfer-order")
public class TransferOrderController {
    private TransferOrderService service;

    public TransferOrderController(TransferOrderService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<TransferOrderResponseDTO> create(@RequestBody TransferOrderRequestDTO dto) throws Exception {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        TransferOrderResponseDTO response = service.create(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
