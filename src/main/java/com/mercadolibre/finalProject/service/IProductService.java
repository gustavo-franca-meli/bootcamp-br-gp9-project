package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductBatchesPurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.enums.ProductType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IProductService {

    ProductResponseDTO create(ProductRequestDTO dto);

    ProductResponseDTO update(Long id, ProductRequestDTO dto);

    void delete(Long id);

    Product findById(Long id) throws ProductNotFoundException;

    List<Product> findAll();

    Set<ProductType> getTypes (Long id) throws ProductNotFoundException;

    Sector findSectorByIdAndWarehouse(Long warehouseId, Product product);

    ProductBatchesPurchaseOrderDTO getProductStockByDate (Long warehouseId, Long productId, LocalDate date, Integer orderQuantity) throws ProductNotFoundException;

    Double getTotalPrice (Long productId, Integer quantity) throws ProductNotFoundException;

    ProductStockDTO getStockForProductInCountryByData (Long productId, Long countryId, LocalDate date);

    List<BatchDTO> getBatchesOfProductInCountry (Long productId, Long countryId, LocalDate date);
}
