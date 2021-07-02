package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.finalProject.service.impl.InboundOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/fresh-products/inboundorder/")
@RestController
public class InboundOrderController {
    private InboundOrderService service;

    public InboundOrderController(InboundOrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InboundOrderResponseDTO> create(@Valid @RequestBody InboundOrderDTO dto, String representative) throws Exception {
        var response = service.create(dto,representative);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping
    public ResponseEntity<InboundOrderResponseDTO> update(@Valid @RequestBody InboundOrderDTO dto, String representative) throws Exception {
        var response = service.save(dto,representative);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
