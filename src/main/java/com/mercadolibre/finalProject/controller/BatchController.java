package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.response.BatchSectorResponseDTO;
import com.mercadolibre.finalProject.service.IBatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/api/v1/fresh-products/due-date")
@RestController
public class BatchController {

    IBatchService batchService;

    public BatchController(IBatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping
    public ResponseEntity<List<BatchSectorResponseDTO>> getBatchesBySector(
            @RequestParam(required = true) Long sectorId) {
        var batches = batchService.getBatchesBySectorId(sectorId);

        return ResponseEntity.ok(batches);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BatchSectorResponseDTO>> getBatchesByProductType(
            @RequestParam(required = true) String category,
            @RequestParam(required = false) String direction) {
        var batches = batchService.getBatchesByProductType(category, direction);

        return ResponseEntity.ok(batches);
    }

}
