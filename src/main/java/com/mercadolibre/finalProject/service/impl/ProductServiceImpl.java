package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.model.mapper.ProductMapper;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.service.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements IProductService {

    private ProductRepository productRepository;
    private ISellerService sellerService;
    private BatchRepository batchRepository;

    public ProductServiceImpl(ProductRepository productRepository, ISellerService sellerService, BatchRepository batchRepository) {
        this.productRepository = productRepository;
        this.sellerService = sellerService;
        this.batchRepository = batchRepository;
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        var seller = sellerService.findSellerById(productRequestDTO.getSellerId());
        var product = new Product(
                productRequestDTO.getName(),
                productRequestDTO.getDescription(),
                productRequestDTO.getPrice(),
                productRequestDTO.getProductType(),
                seller);

        product = productRepository.save(product);

        return ProductMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        var product = this.findProductBy(id);
        product.setName(productRequestDTO.getName() == null ? productRequestDTO.getName() : product.getName());
        product.setDescription(productRequestDTO.getDescription() == null ? productRequestDTO.getDescription() : product.getDescription());
        product.setPrice(productRequestDTO.getPrice() == null ? productRequestDTO.getPrice() : product.getPrice());
        product.setProductType(productRequestDTO.getProductType() == null ? productRequestDTO.getProductType() : product.getProductType());
        product.setSeller(sellerService.findSellerById(productRequestDTO.getSellerId()));

        this.productRepository.save(product);

        return ProductMapper.toResponseDTO(product);
    }

    @Override
    public void delete(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        var product = this.findProductBy(id);
        return ProductMapper.toResponseDTO(product);
    }

    private Product findProductBy(Long id) {
        var product = this.productRepository.findById(id);

        return product.orElseThrow(() -> new ProductNotFoundException("The product doesn't exist. Id: " + id));
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        var products = productRepository.findAll();
        return ProductMapper.toListResponseDTO(products);
    }

    @Override
    public Double getTotalPrice(Long productId, Integer quantity) {
        var product = this.findProductBy(productId);
        return product.getPrice() * quantity;
    }

    @Override
    public ProductStockDTO getStockForProductInCountryByDate (Long productId, Long countryId, LocalDate date) throws ProductNotFoundException {
        Product product = this.findProductBy(productId);
        return new ProductStockDTO(
                productId, product.getName(),product.getPrice(),this.getBatchesOfProductInCountry(productId,countryId,date));
    }

    @Override
    public List<BatchDTO> getBatchesOfProductInCountry (Long productId, Long countryId, LocalDate date) {
        List<Batch> batchesByProductCountryAndDate = this.batchRepository.findByProductCountryAndDate(productId,countryId,date);
        return BatchMapper.toListDTO(batchesByProductCountryAndDate);
    }

    @Override
    public Integer getQuantityOfProductByCountryAndDate (Long productId, Long countryId, LocalDate date) {
        return this.batchRepository.getProductQuantityByCountryAndDate(productId,countryId,date);
    }

    @Override
    public List<ProductResponseDTO> getProductsByCountry() {
        // get country id by token ??
        Long countryId = 5L;
        return ProductMapper.toListResponseDTO(this.productRepository.findByCountry(countryId));
    }
}
