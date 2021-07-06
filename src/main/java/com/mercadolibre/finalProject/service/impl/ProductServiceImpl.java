package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.*;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.exceptions.*;
import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.mapper.*;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.service.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {

    private ProductRepository productRepository;
    private ISellerService sellerService;

    public ProductServiceImpl(ProductRepository productRepository, ISellerService sellerService) {
        this.productRepository = productRepository;
        this.sellerService = sellerService;
    }

    @Override
    public ProductResponseDTO create (ProductRequestDTO productRequestDTO) {
        Product p1 = new Product(
                productRequestDTO.getName(),
                productRequestDTO.getDescription(),
                productRequestDTO.getPrice(),
                new HashSet<>(productRequestDTO.getTypes()),
                sellerService.findSellerById(productRequestDTO.getSellerId()));

        var createdProduct = productRepository.save(p1);

        return ProductMapper.toResponseDTO(createdProduct);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        Product product = this.getModelById(id);
        product.setName(productRequestDTO.getName() == null ? productRequestDTO.getName() : product.getName());
        product.setDescription(productRequestDTO.getDescription() == null ? productRequestDTO.getDescription() : product.getDescription());
        product.setPrice(productRequestDTO.getPrice() == null ? productRequestDTO.getPrice() : product.getPrice());
        product.setTypes(productRequestDTO.getTypes() == null ? new HashSet<>(productRequestDTO.getTypes()) : product.getTypes());
        product.setSeller(sellerService.findSellerById(productRequestDTO.getSellerId()));

        productRepository.save(product);

        return ProductMapper.toResponseDTO(product);
    }

    @Override
    public void delete (Long id) {
        var exists = this.getModelById(id);

        productRepository.deleteById(id);
    }

    @Override
    public ProductResponseDTO findById (Long id) {
        Product product =  this.getModelById(id);

        return ProductMapper.toResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAll() {
        var products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new NotFoundException("List is empty");
        }

        return ProductMapper.toResponseDTOList(products);
    }

    @Override
    public Double getTotalPrice(Long productId, Integer quantity) {
        Product product = this.getModelById(productId);

        return product.getPrice() * quantity;
     }

     @Override
    public ProductStockDTO getStockForProductInCountryByData (Long productId, Long countryId, LocalDate date) {
        Product product = this.getModelById(productId);

        return new ProductStockDTO(productId,
                product.getName(),
                product.getPrice(),
                this.getBatchesOfProductInCountry(productId, countryId, date));
     }

    @Override
    public List<BatchDTO> getBatchesOfProductInCountry (Long productId, Long countryId, LocalDate date) {
        return new ArrayList<>();
    }

    private Product getModelById (Long id) {

        var product =  productRepository.findById(id);

        if(product.isPresent()) return product.get();

        throw new ProductNotFoundException();
    }
}
