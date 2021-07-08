package com.mercadolibre.finalProject.controller;


import com.mercadolibre.finalProject.dtos.request.inboundOrder.InboundOrderCreateRequestDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.InboundOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.finalProject.service.IInboundOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(path = "/api/v1/fresh-products/inboundorder")
@RestController
public class InboundOrderController {
    private IInboundOrderService service;

    public InboundOrderController(IInboundOrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InboundOrderResponseDTO> create(@Valid @RequestBody InboundOrderCreateRequestDTO dto) throws Exception {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var response = service.create(dto, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<InboundOrderResponseDTO> update(@Valid @RequestBody InboundOrderUpdateRequestDTO dto) throws Exception {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var response = service.update(dto, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
