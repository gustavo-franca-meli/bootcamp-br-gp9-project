package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.response.SumOfProductStockDTO;
import com.mercadolibre.finalProject.service.IProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products/warehouse")
public class ProductSumController {

    IProductService service;

    public ProductSumController(IProductService service) {
        this.service = service;
    }

    @GetMapping
    public SumOfProductStockDTO getSumOfProductInAllWarehouses(@RequestParam Long productId , @RequestParam(required = false) Long countryId){
        if(countryId != null){
            return service.getSumOfProductStockByCountry(productId, countryId);
        }else{
            return service.getSumOfProductStockInAllWarehouses(productId);
        }
    }
}