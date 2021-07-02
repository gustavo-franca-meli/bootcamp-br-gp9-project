package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.service.IProductService;
import javassist.NotFoundException;

import java.util.List;

public class ProductServiceImpl implements IProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        var response = productRepository.save(new Product(productRequestDTO.getName(), null));

        return new ProductResponseDTO(response.getId(), response.getName());
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        var optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("product not exists");
        }

        var product = optional.get();
        product.setName(productRequestDTO.getName());
        product.setSeller(null);

        productRepository.save(product);

        return new ProductResponseDTO(product.getId(), product.getName());
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product findById(Long id) throws ProductNotFoundException {

        var product =  productRepository.findById(id);
        if(product.isPresent())return product.get();
        throw new ProductNotFoundException();
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
