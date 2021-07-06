package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductBatchesPurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.ProductDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.enums.ProductType;
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
import java.util.stream.Collectors;

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
    public ProductResponseDTO create (ProductRequestDTO productRequestDTO) {
        Product p1 = new Product();
        p1.setName(productRequestDTO.getName());
        p1.setDescription(productRequestDTO.getDescription());
        p1.setPrice(productRequestDTO.getPrice());
        p1.setTypes((Set<Integer>) productRequestDTO.getTypes());

        var createdProduct = productRepository.save(p1);

        return ProductMapper.toResponseDTO(createdProduct);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) throws ProductNotFoundException {

        Product product = this.getModelById(id);
        product.setName(productRequestDTO.getName());
        product.setSeller(null);

        productRepository.save(product);

        return ProductMapper.toResponseDTO(product);
    }

    @Override
    public void delete (Long id) {
        productRepository.deleteById(id);
    }

    private Product getModelById (Long id) throws ProductNotFoundException {

        var product =  productRepository.findById(id);
        if(product.isPresent())return product.get();
        throw new ProductNotFoundException();
    }

    @Override
    public ProductResponseDTO findById (Long id) throws ProductNotFoundException {

        Product product =  this.getModelById(id);
        return ProductMapper.toResponseDTO(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Double getTotalPrice(Long productId, Integer quantity) throws ProductNotFoundException {
        Product product = this.getModelById(productId);
        return product.getPrice() * quantity;
     }

     @Override
    public ProductStockDTO getStockForProductInCountryByData (Long productId, Long countryId, LocalDate date) throws ProductNotFoundException {
        Product product = this.getModelById(productId);
        return new ProductStockDTO(
                productId, product.getName(),product.getPrice(),this.getBatchesOfProductInCountry(productId,countryId,date));
     }

    @Override
    public List<BatchDTO> getBatchesOfProductInCountry (Long productId, Long countryId, LocalDate date) {
        return new ArrayList<>();
    }

}
