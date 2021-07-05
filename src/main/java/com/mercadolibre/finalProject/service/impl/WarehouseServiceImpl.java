package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.ProductDTO;
import com.mercadolibre.finalProject.dtos.ProductStockForOrderDTO;
import com.mercadolibre.finalProject.dtos.PurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderItemResponseDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.exceptions.WarehouseNotFoundException;
import com.mercadolibre.finalProject.model.Sector;
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
    private IProductService productService;
    private ISectorService sectorService;
    private IBatchService batchService;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, IProductService productService, ISectorService sectorService, IBatchService batchService) {
        this.warehouseRepository = warehouseRepository;
        this.productService = productService;
        this.sectorService = sectorService;
        this.batchService = batchService;
    }

    @Override
    public WarehouseResponseDTO findById(Long warehouseCode) throws WarehouseNotFoundException {
        var warehouse = this.findWarehouseBy(warehouseCode);
        return WarehouseMapper.toResponseDTO(warehouse);
    }

    private Warehouse findWarehouseBy(Long id) {
        var data = warehouseRepository.findById(id);
        return data.orElseThrow(() -> new WarehouseNotFoundException());
    }

    @Override
    public Warehouse findByRepresentative(String representation) throws WarehouseNotFoundException {
        return null;
    }

    @Override
    public List<ProductStockForOrderDTO> getProductsStockForOrder (Long warehouseId, PurchaseOrderDTO purchaseOrder) {

        List<ProductStockForOrderDTO> productsStocks = new ArrayList<>();

        for(ProductDTO productDTO : purchaseOrder.getProducts()) {
            productsStocks.add(this.productService.getProductStockByDate(
                    warehouseId,productDTO.getId(),purchaseOrder.getOrderDate(),productDTO.getQuantity()));
        }
        return productsStocks;
    }

    public Boolean isThereStockForOrder (List<ProductStockForOrderDTO> productsStocks) {
        for(ProductStockForOrderDTO productStock : productsStocks) {
            if( this.sectorService.getProductStockQuantity(productStock) < productStock.getOrderQuantity() ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public PurchaseOrderResponseDTO withDrawStockForOrder(List<ProductStockForOrderDTO> productsStocks) {
        List<PurchaseOrderItemResponseDTO> orderItems = new ArrayList<>();
        Double totalPrice = 0.0;

        for(ProductStockForOrderDTO productStock : productsStocks) {
            Double itemPrice = this.productService.getTotalPrice(productStock.getProductId(),productStock.getOrderQuantity());
            orderItems.add(new PurchaseOrderItemResponseDTO(
                    productStock.getProductId(),
                    productStock.getProductName(),
                    productStock.getOrderQuantity(),
                    itemPrice,
                    this.sectorService.withdrawStockFromBatches(productStock.getBatches(),productStock.getOrderQuantity())));
            totalPrice += itemPrice;
        }

        return new PurchaseOrderResponseDTO(totalPrice,orderItems);
    }
}
