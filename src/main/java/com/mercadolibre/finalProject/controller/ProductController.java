package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fresh-products")
public class ProductController {

    IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    ResponseEntity<List<ProductResponseDTO>> getAllProducts (@PathVariable String token) {
        List<ProductResponseDTO> response = productService.getAllProducts(token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
