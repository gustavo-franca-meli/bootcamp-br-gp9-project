package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.ProductStockForOrderDTO;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.enums.ProductType;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.repository.SectorRepository;
import com.mercadolibre.finalProject.service.IProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        Product p1 = new Product();
        p1.setName(productRequestDTO.getName());
        p1.setTypes((Set<Integer>) productRequestDTO.getTypes());

        var createdProduct = productRepository.save(p1);

        return new ProductResponseDTO(createdProduct.getId(), createdProduct.getName());
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        var optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("Product does not exists");
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

    @Override
    public Sector findSectorByIdAndWarehouse(Long warehouseId, Product product) {
        return null;
    }

    @Override
    public ProductStockForOrderDTO getProductStockByDate(Long warehouseId, Long productId, LocalDate date, Integer orderQuantity) throws ProductNotFoundException {
        Product product = this.findById(productId);
        Sector sector = this.findSectorByIdAndWarehouse(warehouseId,product);
        return new ProductStockForOrderDTO(
                productId,
                product.getName(),
                orderQuantity,
                this.batchRepository.getBatchesOfProductByDate(sector.getId(), productId)); //, date
    }

    @Override
    public Double getTotalPrice(Long productId, Integer quantity) throws ProductNotFoundException {
        Product product = this.findById(productId);
        return 0.0 * quantity; // refactor to include price attribute in product
     }

}
