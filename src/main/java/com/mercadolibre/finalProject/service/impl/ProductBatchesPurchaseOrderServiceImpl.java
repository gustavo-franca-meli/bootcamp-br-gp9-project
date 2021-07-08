package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductPurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.BatchPurchaseOrder;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;
import com.mercadolibre.finalProject.model.PurchaseOrder;
import com.mercadolibre.finalProject.model.mapper.BatchPurchaseOrderMapper;
import com.mercadolibre.finalProject.model.mapper.ProductBatchesPurchaseOrderMapper;
import com.mercadolibre.finalProject.repository.BatchPurchaseOrderRepository;
import com.mercadolibre.finalProject.repository.ProductBatchesPurchaseOrderRepository;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.service.IBatchPurchaseOrderService;
import com.mercadolibre.finalProject.service.IBatchService;
import com.mercadolibre.finalProject.service.IProductBatchesPurchaseOrderService;
import com.mercadolibre.finalProject.service.IProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductBatchesPurchaseOrderServiceImpl implements IProductBatchesPurchaseOrderService {

    ProductBatchesPurchaseOrderRepository repository;
    ProductRepository productRepository;
    BatchPurchaseOrderRepository batchPurchaseOrderRepository;
    IBatchService batchService;
    IProductService productService;
    IBatchPurchaseOrderService batchPurchaseOrderService;

    public ProductBatchesPurchaseOrderServiceImpl(ProductBatchesPurchaseOrderRepository repository, ProductRepository productRepository, BatchPurchaseOrderRepository batchPurchaseOrderRepository, IBatchService batchService, IProductService productService, IBatchPurchaseOrderService batchPurchaseOrderService) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.batchPurchaseOrderRepository = batchPurchaseOrderRepository;
        this.batchService = batchService;
        this.productService = productService;
        this.batchPurchaseOrderService = batchPurchaseOrderService;
    }

    private ProductBatchesPurchaseOrder getModelById (Long id) {
        Optional<ProductBatchesPurchaseOrder> productBatchesOpt = this.repository.findById(id);
        if(productBatchesOpt.isEmpty()) { throw new RuntimeException(); }
        return productBatchesOpt.get();
    }

    @Override
    public ProductBatchesPurchaseOrderResponseDTO findById (Long id) throws ProductNotFoundException {
        ProductBatchesPurchaseOrder productBatches = this.getModelById(id);
        return ProductBatchesPurchaseOrderMapper.toResponseDTO(productBatches);
    }

}
