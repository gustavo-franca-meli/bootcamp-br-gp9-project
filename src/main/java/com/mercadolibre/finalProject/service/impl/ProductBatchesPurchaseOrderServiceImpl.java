package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.ProductPurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.exceptions.ProductNotFoundException;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;
import com.mercadolibre.finalProject.model.PurchaseOrder;
import com.mercadolibre.finalProject.model.mapper.BatchPurchaseOrderMapper;
import com.mercadolibre.finalProject.model.mapper.ProductBatchesPurchaseOrderMapper;
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
    IBatchService batchService;
    IProductService productService;
    IBatchPurchaseOrderService batchPurchaseOrderService;

    public ProductBatchesPurchaseOrderServiceImpl(ProductBatchesPurchaseOrderRepository repository, IBatchService batchService, IProductService productService, IBatchPurchaseOrderService batchPurchaseOrderService) {
        this.repository = repository;
        this.batchService = batchService;
        this.productService = productService;
        this.batchPurchaseOrderService = batchPurchaseOrderService;
    }

    private ProductBatchesPurchaseOrder getModelById (Long id) {
        Optional<ProductBatchesPurchaseOrder> productBatchesOpt = this.repository.findById(id);
        if(productBatchesOpt.isEmpty()) { throw new RuntimeException(); }
        return productBatchesOpt.get();
    }

    private void downOrderQuantity (ProductBatchesPurchaseOrder productBatches) {

    }

    private void upOrderQuantity (ProductBatchesPurchaseOrder productBatches) {

    }


    @Override
    public ProductBatchesPurchaseOrderResponseDTO findById (Long id) throws ProductNotFoundException {
        ProductBatchesPurchaseOrder productBatches = this.getModelById(id);
        ProductResponseDTO product = this.productService.findById(productBatches.getProductId());

        return ProductBatchesPurchaseOrderMapper.toResponseDTO(productBatches, product.getName(), BatchPurchaseOrderMapper.toListResponseDTO(productBatches.getPurchaseBatches()));
    }

    @Override
    public ProductBatchesPurchaseOrderResponseDTO updateQuantity (Long id, PurchaseOrderUpdateRequestDTO updateRequest) {
        ProductBatchesPurchaseOrder productBatches = this.getModelById(id);

        if(productBatches.getTotalQuantity() > updateRequest.getNewQuantity()) {
            this.downOrderQuantity(productBatches);
        }
        else {
            this.upOrderQuantity(productBatches);
        }

        return new ProductBatchesPurchaseOrderResponseDTO();
    }

    @Override
    public ProductBatchesPurchaseOrderResponseDTO create (ProductPurchaseOrderRequestDTO productRequest, PurchaseOrder purchaseOrder, Long countryId, LocalDate date) throws ProductNotFoundException {
        List<BatchPurchaseOrderResponseDTO> batchesOrder = new ArrayList<>();
        Integer withdrawnQuantity = 0, orderQuantity = productRequest.getQuantity();

        ProductStockDTO productStock = this.productService.getStockForProductInCountryByData(productRequest.getProductId(), countryId, date);
        ProductBatchesPurchaseOrder productBatchesPurchaseOrder = new ProductBatchesPurchaseOrder(productStock.getProductId(),productStock.getProductPrice(),purchaseOrder);

        for(BatchDTO batchDTO : productStock.getBatches()) {
            if(withdrawnQuantity >= orderQuantity) { break; }

            Integer quantityFromBatch = this.calculateQuantity(batchDTO.getCurrentQuantity(),orderQuantity - withdrawnQuantity);
            batchesOrder.add(this.batchPurchaseOrderService.create(batchDTO.getId(),quantityFromBatch,productBatchesPurchaseOrder));
            withdrawnQuantity += quantityFromBatch;
        }

        this.repository.save(productBatchesPurchaseOrder);
        return ProductBatchesPurchaseOrderMapper.toResponseDTO(productBatchesPurchaseOrder,productStock.getProductName(),batchesOrder);
   }

    private Integer calculateQuantity (Integer batchCurrentQuantity, Integer quantityLeftForOrder) {
        if(batchCurrentQuantity > quantityLeftForOrder) {
            return quantityLeftForOrder;
        }
        return batchCurrentQuantity;
    }

}
