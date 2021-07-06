package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.model.Seller;
import com.mercadolibre.finalProject.service.ISellerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    ISellerService service;

    public SellerController(ISellerService service) {
        this.service = service;
    }

    @PostMapping
    public Long createSeller(@RequestBody Seller seller) {
        return service.createSeller(seller);
    }

    @GetMapping("/{id}")
    public Seller findSellerById(@PathVariable Long id) {
        return service.findSellerById(id);
    }

    @GetMapping
    public List<Seller> listAllSellers() {
        return service.listAllSellers();
    }

    @PutMapping
    public Seller updateSeller(@RequestBody Seller seller) {
        return service.updateSeller(seller);
    }
}
