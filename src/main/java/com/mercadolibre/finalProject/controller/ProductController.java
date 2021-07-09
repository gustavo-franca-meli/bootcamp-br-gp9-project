package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.request.SectorBatchRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.dtos.response.SectorBatchResponseDTO;
import com.mercadolibre.finalProject.service.IBatchService;
import com.mercadolibre.finalProject.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/fresh-products")
public class ProductController {

    private final IBatchService batchService;
    private final IProductService productService;

    public ProductController(IBatchService batchService, IProductService productService) {
        this.batchService = batchService;
        this.productService = productService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<SectorBatchResponseDTO> getSectorBatchesByProductId(@RequestParam Long productId, @RequestParam(required = false) String ordered) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var request = new SectorBatchRequestDTO(productId, auth.getName(), ordered);

        var response = this.batchService.getSectorBatchesByProductId(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    ResponseEntity<List<ProductResponseDTO>> getProductsByCountry () {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        List<ProductResponseDTO> response = productService.getProductsByCountry(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
