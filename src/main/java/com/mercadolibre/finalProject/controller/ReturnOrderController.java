package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.request.ReturnOrderRequestDTO;
import com.mercadolibre.finalProject.service.IReturnOrderService;
import org.eclipse.jetty.server.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products/return-order")
public class ReturnOrderController {

    private final IReturnOrderService returnOrderService;

    public ReturnOrderController(IReturnOrderService returnOrderService) {
        this.returnOrderService = returnOrderService;
    }

    @PostMapping
    public ResponseEntity<Void> orderReturnRequest(@RequestBody ReturnOrderRequestDTO dto) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        dto.setBuyerUsername(username);
        this.returnOrderService.returnOrder(dto);

        return ResponseEntity.noContent().build();
    }
}
