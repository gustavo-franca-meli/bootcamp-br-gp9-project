package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.request.UpdatePurchaseOrderStatusRequestDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.service.IPurchaseOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/purchase-order")
public class PurchaseOrderController {

    IPurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(IPurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping("/create")
    public ResponseEntity<PurchaseOrderResponseDTO> create(@RequestBody PurchaseOrderRequestDTO dto) throws Exception {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        PurchaseOrderResponseDTO response = purchaseOrderService.create(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PurchaseOrderResponseDTO> getById(@RequestParam Long purchaseOrderId) throws Exception {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        PurchaseOrderResponseDTO response = purchaseOrderService.getById(purchaseOrderId, username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PurchaseOrderResponseDTO>> getAll() throws Exception {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        List<PurchaseOrderResponseDTO> response = purchaseOrderService.getAll(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<PurchaseOrderResponseDTO> update(@RequestBody PurchaseOrderUpdateRequestDTO dto) throws Exception {
        PurchaseOrderResponseDTO response = purchaseOrderService.update(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("update-status")
    public ResponseEntity<Void> updateStatus(@RequestBody UpdatePurchaseOrderStatusRequestDTO dto) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        dto.setRepresentativeUsername(username);
        this.purchaseOrderService.updateStatus(dto);

        return ResponseEntity.noContent().build();
    }


}
