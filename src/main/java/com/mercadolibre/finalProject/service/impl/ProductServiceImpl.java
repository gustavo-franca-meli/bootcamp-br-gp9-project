package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.mapper.ProductMapper;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.repository.SectorRepository;
import com.mercadolibre.finalProject.service.IProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements IProductService {

    private ProductRepository productRepository;
    private SectorRepository sectorRepository;
    private BatchRepository batchRepository;

    public ProductServiceImpl(ProductRepository productRepository, SectorRepository sectorRepository, BatchRepository batchRepository) {
        this.productRepository = productRepository;
        this.sectorRepository = sectorRepository;
        this.batchRepository = batchRepository;
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        var product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setProductType(productRequestDTO.getProductType());

        var createdProduct = productRepository.save(product);

        return ProductMapper.toResponseDTO(createdProduct);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        Product product = this.findProductBy(id);
        product.setName(productRequestDTO.getName());
        product.setSeller(null);

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
    public ProductStockDTO getStockForProductInCountryByData(Long productId, Long countryId, LocalDate date) {
        Product product = this.findProductBy(productId);
        return new ProductStockDTO(
                productId, product.getName(), product.getPrice(), this.getBatchesOfProductInCountry(productId, countryId, date));
    }

    @Override
    public List<BatchDTO> getBatchesOfProductInCountry(Long productId, Long countryId, LocalDate date) {
        return new ArrayList<>();
    }

}
