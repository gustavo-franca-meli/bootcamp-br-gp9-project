package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.dtos.response.SumOfProductStockDTO;
import com.mercadolibre.finalProject.dtos.response.WarehouseProductSumDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.enums.ProductType;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.model.mapper.ProductMapper;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.service.IAccountService;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.ISellerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private ProductRepository productRepository;
    private IAccountService accountService;
    private ISellerService sellerService;
    private BatchRepository batchRepository;

    public ProductServiceImpl(ProductRepository productRepository, IAccountService accountService, ISellerService sellerService, BatchRepository batchRepository) {
        this.productRepository = productRepository;
        this.accountService = accountService;
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
        product.setName(productRequestDTO.getName() != null ? productRequestDTO.getName() : product.getName());
        product.setDescription(productRequestDTO.getDescription() != null ? productRequestDTO.getDescription() : product.getDescription());
        product.setPrice(productRequestDTO.getPrice() != null ? productRequestDTO.getPrice() : product.getPrice());
        product.setProductType(productRequestDTO.getProductType() != null ? productRequestDTO.getProductType() : product.getProductType());
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
        if( this.batchRepository.getProductQuantityByCountryAndDate(productId, countryId, date) == null ){
            return 0;
        }
        return this.batchRepository.getProductQuantityByCountryAndDate(productId, countryId, date);
    }

    @Override
    public List<ProductResponseDTO> getProductsByCountry(String username, Integer productType) {
        Long countryId = this.accountService.getAccountByUsername(username).getCountry().getId();

        if(productType == null) {
            return ProductMapper.toListResponseDTO(this.productRepository.findByCountry(countryId));
        }

        ProductType productTypeEnum = ProductType.toEnum(productType); // checks if product type is valid
        return ProductMapper.toListResponseDTO(this.productRepository.findByCountryAndType(countryId,productType));
    }

    @Override
    public SumOfProductStockDTO getSumOfProductStockInAllWarehouses(Long productId) {
        List<ProductRepository.ISumOfProductStockDTO> query = productRepository.getSumOfProductStockInAllWarehouses(productId);

        List<WarehouseProductSumDTO> dto = new ArrayList<>();

        query.forEach(c -> dto.add(new WarehouseProductSumDTO(Long.valueOf(c.getWarehouse_id()), Integer.valueOf(c.getQuantity()))));

//        if (dto.isEmpty()){
//            throw new RuntimeException("Não existe produto com o id: " + productId);
//        }
        return new SumOfProductStockDTO(productId, dto);
    }
}
