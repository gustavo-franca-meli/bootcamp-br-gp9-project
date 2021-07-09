package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.response.ProductBatchesPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;
import com.mercadolibre.finalProject.repository.ProductBatchesPurchaseOrderRepository;
import com.mercadolibre.finalProject.service.IProductBatchesPurchaseOrderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductBatchesPurchaseOrderServiceImpl implements IProductBatchesPurchaseOrderService {

    ProductBatchesPurchaseOrderRepository repository;

    public ProductBatchesPurchaseOrderServiceImpl(ProductBatchesPurchaseOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductBatchesPurchaseOrderResponseDTO findById(Long id) {
        ProductBatchesPurchaseOrder productBatches = this.getModelById(id);

        return new ProductBatchesPurchaseOrderResponseDTO();
    }

    private ProductBatchesPurchaseOrder getModelById (Long id) {
        Optional<ProductBatchesPurchaseOrder> productBatchesOpt = this.repository.findById(id);
        if (productBatchesOpt.isEmpty()) {
            throw new RuntimeException();
        }
        return productBatchesOpt.get();
    }

}
