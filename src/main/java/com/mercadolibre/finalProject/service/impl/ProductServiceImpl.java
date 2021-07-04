package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.ProductStockForOrderDTO;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.model.enums.SectorType;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.repository.SectorRepository;
import com.mercadolibre.finalProject.service.IProductService;
import javassist.NotFoundException;
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
        var response = productRepository.save(new Product(productRequestDTO.getName(), new HashSet<>(productRequestDTO.getTypes()), null));

        return new ProductResponseDTO(response.getId(), response.getName());
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
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Set<SectorType> getTypes(Long id) {
        Product product = this.findById(id);
        return product.getTypes().stream().map(SectorType::toEnum).collect(Collectors.toSet());
    }

    @Override
    public Sector findSectorByIdAndWarehouse(Long warehouseId, Product product) {
        Optional<Sector> sector = product.getSectors().stream().filter(
                s -> s.getWarehouse().getId().equals(warehouseId)).collect(Collectors.toList()).stream().findFirst();
        if(sector.isEmpty()) { throw new RuntimeException(); } // CREATE EXCEPTION FOR WHEN CANT FIND SECTOR FOR PRODUCT AND WAREHOUSE
        return sector.get();
    }

    @Override
    public ProductStockForOrderDTO getProductStockByDate(Long warehouseId, Long productId, LocalDate date, Integer orderQuantity) {
        Product product = this.findById(productId);
        Sector sector = this.findSectorByIdAndWarehouse(warehouseId,product);
        return new ProductStockForOrderDTO(
                productId,
                product.getName(),
                orderQuantity,
                this.batchRepository.getBatchesOfProductByDate(sector.getId(), productId)); //, date
    }

    @Override
    public Double getTotalPrice(Long productId, Integer quantity) {
        Product product = this.findById(productId);
        return 0.0 * quantity; // refactor to include price attribute in product
     }

}
