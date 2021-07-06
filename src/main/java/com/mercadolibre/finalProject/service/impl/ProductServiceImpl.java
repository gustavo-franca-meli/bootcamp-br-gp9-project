package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductBatchesPurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
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
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
//        var response = productRepository.save(new Product(productRequestDTO.getName(), new HashSet<>(productRequestDTO.getTypes()), null));
//
//        return new ProductResponseDTO(response.getId(), response.getName());
        return null;
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
    public Set<ProductType> getTypes(Long id) throws ProductNotFoundException {
        Product product = this.findById(id);
        return product.getTypes().stream().map(ProductType::toEnum).collect(Collectors.toSet());
    }

    @Override
    public Sector findSectorByIdAndWarehouse(Long warehouseId, Product product) {
        return null;
    }

    @Override
    public ProductBatchesPurchaseOrderDTO getProductStockByDate(Long warehouseId, Long productId, LocalDate date, Integer orderQuantity) throws ProductNotFoundException {
//        Product product = this.findById(productId);
//        Sector sector = this.findSectorByIdAndWarehouse(warehouseId,product);
//        return new ProductBatchesPurchaseOrderDTO(
//                productId,
//                product.getName(),
//                orderQuantity,
//                this.batchRepository.getBatchesOfProductByDate(sector.getId(), productId, date)); //, date
        return null;
    }

    @Override
    public Double getTotalPrice(Long productId, Integer quantity) throws ProductNotFoundException {
        Product product = this.findById(productId);
        return 0.0 * quantity; // refactor to include price attribute in product
     }

     @Override
    public ProductStockDTO getStockForProductInCountryByData (Long productId, Long countryId, LocalDate date) {
        Product product = this.productRepository.findById(productId).get(); //arrumar isso aqui
        return new ProductStockDTO(
                productId, product.getName(),product.getPrice(),this.getBatchesOfProductInCountry(productId,countryId,date));
     }

    @Override
    public List<BatchDTO> getBatchesOfProductInCountry (Long productId, Long countryId, LocalDate date) {
        return new ArrayList<>();
    }

}
