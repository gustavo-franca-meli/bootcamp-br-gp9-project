package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.ProductStockForOrderDTO;
import com.mercadolibre.finalProject.dtos.PurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.model.enums.SectorType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IProductService {

    ProductResponseDTO create(ProductRequestDTO dto);

    ProductResponseDTO update(Long id, ProductRequestDTO dto);

    void delete(Long id);

    Product findById(Long id);

    List<Product> findAll();

    Set<SectorType> getTypes (Long id);

    Sector findSectorByIdAndWarehouse(Long warehouseId, Product product);

    ProductStockForOrderDTO getProductStockByDate (Long warehouseId, Long productId, LocalDate date, Integer orderQuantity);

    Double getTotalPrice (Long productId, Integer quantity);
}
