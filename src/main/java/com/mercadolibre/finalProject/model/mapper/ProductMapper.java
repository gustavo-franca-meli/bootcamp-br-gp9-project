package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public interface ProductMapper {

    static List<ProductResponseDTO> toResponseDTOList(List<Product> products) {
        return products.stream().map(ProductMapper::toResponseDTO).collect(Collectors.toList());
    }

    static ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getTypes()
        );
    }
}
