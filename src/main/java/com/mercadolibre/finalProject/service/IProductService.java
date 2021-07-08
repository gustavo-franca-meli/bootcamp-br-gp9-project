package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.Product;

import java.time.LocalDate;
import java.util.List;

public interface IProductService {

    ProductResponseDTO findById (Long id) throws ProductNotFoundException;
    ProductResponseDTO create(ProductRequestDTO dto);
    ProductResponseDTO update(Long id, ProductRequestDTO dto) throws ProductNotFoundException;
    void delete(Long id);
    List<Product> findAll();
    Double getTotalPrice (Long productId, Integer quantity) throws ProductNotFoundException;
    ProductStockDTO getStockForProductInCountryByData (Long productId, Long countryId, LocalDate date) throws ProductNotFoundException;
    List<BatchDTO> getBatchesOfProductInCountry (Long productId, Long countryId, LocalDate date);
    Integer getQuantityOfProductByCountryAndDate (Long productId, Long countryId, LocalDate date);
    List<ProductResponseDTO> getProductsByCountry ();
}
