package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.model.Product;

import java.util.List;

public interface IProductService {

    ProductResponseDTO create(ProductRequestDTO dto);

    ProductResponseDTO update(Long id, ProductRequestDTO dto);

    void delete(Long id);

    ProductResponseDTO getById(Long id);

    List<ProductResponseDTO> getAll();

}
