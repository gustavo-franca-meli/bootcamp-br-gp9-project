package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;
import com.mercadolibre.finalProject.model.PurchaseOrder;
import com.mercadolibre.finalProject.repository.ProductBatchesPurchaseOrderRepository;
import com.mercadolibre.finalProject.service.IBatchPurchaseOrderService;
import com.mercadolibre.finalProject.service.IProductBatchesPurchaseOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductBatchesPurchaseOrderServiceImpl implements IProductBatchesPurchaseOrderService {

    ProductBatchesPurchaseOrderRepository repository;
    IBatchPurchaseOrderService batchPurchaseOrderService;

    public ProductBatchesPurchaseOrderServiceImpl(ProductBatchesPurchaseOrderRepository repository, IBatchPurchaseOrderService batchPurchaseOrderService) {
        this.repository = repository;
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
    public ProductBatchesPurchaseOrderResponseDTO findById(Long id) {
        ProductBatchesPurchaseOrder productBatches = this.getModelById(id);

        return new ProductBatchesPurchaseOrderResponseDTO();
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
    public ProductBatchesPurchaseOrderResponseDTO create (Integer orderQuantity, ProductStockDTO productStock, PurchaseOrder purchaseOrder) {
        List<BatchPurchaseOrderResponseDTO> batchesOrder = new ArrayList<>();
        Integer withdrawnQuantity = 0;
        ProductBatchesPurchaseOrder productBatchesPurchaseOrder = new ProductBatchesPurchaseOrder(productStock.getProductPrice(),purchaseOrder);

        for(BatchDTO batchDTO : productStock.getBatches()) {
            if(withdrawnQuantity >= orderQuantity) { break; }

            Integer quantityFromBatch = this.calculateQuantity(batchDTO.getCurrentQuantity(),orderQuantity - withdrawnQuantity);
            batchesOrder.add(this.batchPurchaseOrderService.create(batchDTO.getId(),quantityFromBatch,productBatchesPurchaseOrder));
            withdrawnQuantity += quantityFromBatch;
        }

        this.repository.save(productBatchesPurchaseOrder);
        return new ProductBatchesPurchaseOrderResponseDTO(productStock.getProductId(),productStock.getProductName(),orderQuantity,productStock.getProductPrice(),batchesOrder);
    }

    private Integer calculateQuantity (Integer batchCurrentQuantity, Integer quantityLeftForOrder) {
        if(batchCurrentQuantity > quantityLeftForOrder) {
            return quantityLeftForOrder;
        }
        return batchCurrentQuantity;
    }

}
