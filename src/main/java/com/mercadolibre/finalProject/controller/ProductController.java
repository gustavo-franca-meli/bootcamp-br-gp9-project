package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.request.SectorBatchRequestDTO;
import com.mercadolibre.finalProject.dtos.response.SectorBatchResponseDTO;
import com.mercadolibre.finalProject.service.IBatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/fresh-products")
public class ProductController {

    private final IBatchService batchService;

    public ProductController(IBatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<SectorBatchResponseDTO> getSectorBatchesByProductId(@RequestParam Long productId, @RequestParam(required = false) String ordered) {
        var request = new SectorBatchRequestDTO(productId, 1L, ordered);
        var response = this.batchService.getSectorBatchesByProductId(request);
        return ResponseEntity.ok().body(response);
    }
}
