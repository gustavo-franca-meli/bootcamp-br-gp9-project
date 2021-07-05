package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.ProductStockForOrderDTO;
import com.mercadolibre.finalProject.dtos.PurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.IPurchaseOrderService;
import com.mercadolibre.finalProject.service.ISectorService;
import com.mercadolibre.finalProject.service.IWarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {
    private IProductService productService;
    private IWarehouseService warehouseService;
    private ISectorService sectorService;

    public PurchaseOrderServiceImpl(IProductService productService, IWarehouseService warehouseService, ISectorService sectorService) {
        this.productService = productService;
        this.warehouseService = warehouseService;
        this.sectorService = sectorService;
    }

    @Override
    public PurchaseOrderResponseDTO create(PurchaseOrderDTO purchaseOrder, String representative) throws WarehouseNotFoundException {
//        //buyer registered
//
//        Warehouse warehouse = this.warehouseService.findByRepresentative(representative);
//
//        List<ProductStockForOrderDTO> productsStocks = this.warehouseService.getProductsStockForOrder(warehouse.getId(),purchaseOrder);
//
//        if(!this.warehouseService.isThereStockForOrder(productsStocks)) {
//            return null;
//        }
//
//        PurchaseOrderResponseDTO purchaseOrderResponse = this.warehouseService.withDrawStockForOrder(productsStocks);
//        purchaseOrder.setOrderDate(purchaseOrder.getOrderDate());
//        return purchaseOrderResponse;

        return null;
    }
}
