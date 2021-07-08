package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.response.BatchValidateDateResponseDTO;
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
    public ResponseEntity<List<BatchValidateDateResponseDTO>> getBatchesBySector(
            @RequestParam(required = true) Long sectorId,
            @RequestParam(required = true) Integer daysQuantity) {
        var batches = batchService.getBatchesBySectorId(sectorId, daysQuantity);

        return ResponseEntity.ok(batches);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BatchValidateDateResponseDTO>> getBatchesByProductType(
            @RequestParam(required = true) Integer daysQuantity,
            @RequestParam(required = true) String category,
            @RequestParam(required = false) String direction) {
        var batches = batchService.getBatchesByProductType(daysQuantity, category, direction);

        return ResponseEntity.ok(batches);
    }

}
