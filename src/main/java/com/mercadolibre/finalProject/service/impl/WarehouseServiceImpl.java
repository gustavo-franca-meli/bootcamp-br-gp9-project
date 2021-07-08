package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.ProductDTO;
import com.mercadolibre.finalProject.dtos.ProductBatchesPurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.PurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.model.mapper.WarehouseMapper;
import com.mercadolibre.finalProject.repository.WarehouseRepository;
import com.mercadolibre.finalProject.service.IBatchService;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.ISectorService;
import com.mercadolibre.finalProject.service.IWarehouseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseServiceImpl implements IWarehouseService {

    private WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public WarehouseResponseDTO findById(Long warehouseId) throws WarehouseNotFoundException {
        var warehouse = this.findWarehouseBy(warehouseId);
        return WarehouseMapper.toResponseDTO(warehouse);
    }

    private Warehouse findWarehouseBy(Long warehouseId) {
        var data = warehouseRepository.findById(warehouseId);
        return data.orElseThrow(() -> new WarehouseNotFoundException("Warehouse Not Found. Id:" + warehouseId));
    }

    @Override
    public Warehouse findByRepresentative(String representation) throws WarehouseNotFoundException {
        return null;
    }

    @Override
    public List<ProductBatchesPurchaseOrderDTO> getProductsStockForOrder (Long warehouseId, PurchaseOrderDTO purchaseOrder) throws ProductNotFoundException {
//
//        List<ProductBatchesPurchaseOrderDTO> productsStocks = new ArrayList<>();
//
//        for(ProductDTO productDTO : purchaseOrder.getProducts()) {
//            productsStocks.add(this.productService.getProductStockByDate(
//                    warehouseId,productDTO.getId(),purchaseOrder.getOrderDate(),productDTO.getQuantity()));
//        }
//        return productsStocks;

        return null;
    }

    public Boolean isThereStockForOrder (List<ProductBatchesPurchaseOrderDTO> productsStocks) {
//        for(ProductBatchesPurchaseOrderDTO productStock : productsStocks) {
//            if( this.sectorService.getProductStockQuantity(productStock) < productStock.getOrderQuantity() ) {
//                return false;
//            }
//        }
        return true;
    }

    @Override
    public PurchaseOrderResponseDTO withDrawStockForOrder(List<ProductBatchesPurchaseOrderDTO> productsStocks) throws ProductNotFoundException {
//        List<ProductBatchesPurchaseOrderResponseDTO> orderItems = new ArrayList<>();
//        Double totalPrice = 0.0;
//
//        for(ProductBatchesPurchaseOrderDTO productStock : productsStocks) {
//            Double itemPrice = this.productService.getTotalPrice(productStock.getProductId(),productStock.getOrderQuantity());
//            orderItems.add(new ProductBatchesPurchaseOrderResponseDTO(
//                    productStock.getProductId(),
//                    productStock.getProductName(),
//                    productStock.getOrderQuantity(),
//                    itemPrice,
//                    this.sectorService.withdrawStockFromBatches(productStock.getBatches(),productStock.getOrderQuantity())));
//            totalPrice += itemPrice;
//        }
//
//        return new PurchaseOrderResponseDTO(totalPrice,orderItems);
        return null;
    }
}
