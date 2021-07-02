package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.model.Product;

public interface IProductService {
    Product findById(String productId);
}
