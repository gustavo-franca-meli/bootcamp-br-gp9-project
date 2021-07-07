package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.response.BatchSectorResponseDTO;
import com.mercadolibre.finalProject.service.IBatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(path = "/api/v1/fresh-products/due-date")
@RestController
public class BatchController {

    IBatchService batchService;

    public BatchController(IBatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping
    public ResponseEntity<List<BatchSectorResponseDTO>> getBatchesBySector(@RequestParam(required = true) Long sectorId) {
        var batches = batchService.getBatchesBySectorId(sectorId);

        return ResponseEntity.ok(batches);
    }

}
