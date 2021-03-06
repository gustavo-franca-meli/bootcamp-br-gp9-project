package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.enums.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ProductMapper {

    static List<ProductResponseDTO> toListResponseDTO(List<Product> products) {
        if (products.isEmpty())
            return new ArrayList<>();
        return products.stream().map(ProductMapper::toResponseDTO).collect(Collectors.toList());
    }

    static ProductResponseDTO toResponseDTO(Product product) {
        String productType = null;
        if (product.getProductType() != null) {
            productType = ProductType.toEnum(product.getProductType()).getDescription();
        }

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getProductType(),
                productType
        );
    }
}
