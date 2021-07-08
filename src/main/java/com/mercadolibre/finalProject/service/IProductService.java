package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface IProductService {

    ProductResponseDTO create(ProductRequestDTO dto);

    ProductResponseDTO update(Long id, ProductRequestDTO dto);

    void delete(Long id);

    ProductResponseDTO findById(Long id);

    List<ProductResponseDTO> findAll();

    Double getTotalPrice(Long productId, Integer quantity);

    ProductStockDTO getStockForProductInCountryByData(Long productId, Long countryId, LocalDate date);

    List<BatchDTO> getBatchesOfProductInCountry(Long productId, Long countryId, LocalDate date);
}
