package com.mercadolibre.finalProject.controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class InboundOrderController {


=======
import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.finalProject.service.impl.InboundOrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/fresh-products/inboundorder/")
@RestController
public class InboundOrderController {
    private InboundOrderServiceImpl service;

    public InboundOrderController(InboundOrderServiceImpl service) {
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
>>>>>>> 718b4364f27e7a1153054697866472d2621cca21

}
