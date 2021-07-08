package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.dtos.response.SumOfProductStockDTO;
import com.mercadolibre.finalProject.dtos.response.WarehouseProductSumDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.mapper.ProductMapper;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.repository.WarehouseRepository;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.ISellerService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class ProductServiceImpl implements IProductService {

    private ProductRepository productRepository;
    private ISellerService sellerService;
    private WarehouseRepository warehouseRepository;

    public ProductServiceImpl(ProductRepository productRepository, ISellerService sellerService, WarehouseRepository warehouseRepository) {
        this.productRepository = productRepository;
        this.sellerService = sellerService;
        this.warehouseRepository = warehouseRepository;
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
    public ProductStockDTO getStockForProductInCountryByData(Long productId, Long countryId, LocalDate date) {
        Product product = this.findProductBy(productId);
        return new ProductStockDTO(
                productId, product.getName(), product.getPrice(), this.getBatchesOfProductInCountry(productId, countryId, date));
    }

    @Override
    public List<BatchDTO> getBatchesOfProductInCountry(Long productId, Long countryId, LocalDate date) {
        return new ArrayList<>();
    }

    @Override
    public SumOfProductStockDTO getSumOfProductStockInAllWarehouses(Long productId) {
        List<WarehouseRepository.ISumOfProductStockDTO> query = warehouseRepository.getSumOfProductStockInAllWarehouses(productId);

        List<WarehouseProductSumDTO> dto = new ArrayList<WarehouseProductSumDTO>();

        query.forEach(c -> dto.add(new WarehouseProductSumDTO(Long.valueOf(c.getWarehouse_id()), Integer.valueOf(c.getQuantity()))));

        return new SumOfProductStockDTO(productId, dto);
    }


    @Override
    public SumOfProductStockDTO getSumOfProductStockByCountry(Long productId, Long countryId) {
        SumOfProductStockDTO dto = new SumOfProductStockDTO();
        dto.setProductId(productId);
        List<WarehouseProductSumDTO> warehouses = warehouseRepository.getSumOfProductsByCountry(productId, countryId).stream()
                .map(x -> new WarehouseProductSumDTO(Long.valueOf(x.getWarehouse_id()), Integer.valueOf(x.getQuantity()))).collect(Collectors.toList());

        return new SumOfProductStockDTO(productId, warehouses);
    }
}
