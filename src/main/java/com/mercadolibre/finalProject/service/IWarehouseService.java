package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.ProductBatchesPurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.PurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Warehouse;

import java.util.List;

public interface IWarehouseService {
    WarehouseResponseDTO findById(Long warehouseCode) throws WarehouseNotFoundException;
    Warehouse findByRepresentative (String representation) throws WarehouseNotFoundException;
    List<ProductBatchesPurchaseOrderDTO> getProductsStockForOrder (Long id, PurchaseOrderDTO purchaseOrder) throws ProductNotFoundException;
    Boolean isThereStockForOrder (List<ProductBatchesPurchaseOrderDTO> productsStocks);
    PurchaseOrderResponseDTO withDrawStockForOrder(List<ProductBatchesPurchaseOrderDTO> productsStocks) throws ProductNotFoundException;
}
