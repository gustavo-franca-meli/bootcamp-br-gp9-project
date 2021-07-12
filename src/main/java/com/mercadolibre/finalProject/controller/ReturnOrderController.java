package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.request.ReturnOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.UpdateReturnOrderRequestDTO;
import com.mercadolibre.finalProject.service.IReturnOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/return-order")
public class ReturnOrderController {

    private final IReturnOrderService returnOrderService;

    public ReturnOrderController(IReturnOrderService returnOrderService) {
        this.returnOrderService = returnOrderService;
    }

    @PostMapping
    public ResponseEntity<Void> returnOrderRequest(@RequestBody ReturnOrderRequestDTO dto) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        dto.setBuyerUsername(username);
        this.returnOrderService.returnOrder(dto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> returnOrderRequest(@RequestBody UpdateReturnOrderRequestDTO dto) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        dto.setUsername(username);
        this.returnOrderService.updateStatus(dto);

        return ResponseEntity.noContent().build();
    }
}
