package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.exceptions.NotFoundException;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.mapper.ProductMapper;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.repository.SellerRepository;
import com.mercadolibre.finalProject.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    ProductRepository productRepository;
    SellerRepository sellerRepository;

    public ProductServiceImpl(ProductRepository productRepository, SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        var seller = sellerRepository.findById(productRequestDTO.getSellerId());
        if (seller.isEmpty()) {
            throw new NotFoundException("Seller not exists");
        }

        var response = productRepository.save(new Product(productRequestDTO.getName(), seller.get()));

        return ProductMapper.fromEntityToResponse(response);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        var optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            throw new NotFoundException("Product not exists");
        }

        var product = optional.get();
        product.setName(productRequestDTO.getName());
        sellerRepository.findById(productRequestDTO.getSellerId()).ifPresent(product::setSeller);

        productRepository.save(product);

        return ProductMapper.fromEntityToResponse(product);
    }

    @Override
    public void delete(Long id) {
        var optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            throw new NotFoundException("Product not exists");
        }

        productRepository.deleteById(id);
    }

    @Override
    public ProductResponseDTO getById(Long id) {
        var optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            throw new NotFoundException("Product not exists");
        }

        return ProductMapper.fromEntityToResponse(optional.get());
    }

    @Override
    public List<ProductResponseDTO> getAll() {
        var products = productRepository.findAll();

        if (products == null || products.isEmpty()) {
            throw new NotFoundException("List is empty");
        }

        return ProductMapper.fromEntityListToResponse(products);
    }
}
