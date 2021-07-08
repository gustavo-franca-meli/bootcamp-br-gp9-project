package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.NotFoundException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.BatchPurchaseOrder;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;
import com.mercadolibre.finalProject.model.mapper.BatchPurchaseOrderMapper;
import com.mercadolibre.finalProject.repository.BatchPurchaseOrderRepository;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.service.IBatchPurchaseOrderService;
import com.mercadolibre.finalProject.service.IBatchService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BatchPurchaseOrderServiceImpl implements IBatchPurchaseOrderService {

    private BatchPurchaseOrderRepository repository;
    private BatchRepository batchRepository;
    private IBatchService batchService;

    public BatchPurchaseOrderServiceImpl(BatchPurchaseOrderRepository repository, BatchRepository batchRepository, IBatchService batchService) {
        this.repository = repository;
        this.batchRepository = batchRepository;
        this.batchService = batchService;
    }

    private BatchPurchaseOrder getModelById (Long id) {
        Optional<BatchPurchaseOrder> batchPurchaseOpt = this.repository.findById(id);
        if(batchPurchaseOpt.isEmpty()) { throw new NotFoundException("Purchase Order Batch ID " + id + " invalid"); } //can't find batch purchase order

        return batchPurchaseOpt.get();
    }

    @Override
    public BatchPurchaseOrderResponseDTO findById (Long id) {

        BatchPurchaseOrder batchPurchaseOrder = this.getModelById(id);

        return BatchPurchaseOrderMapper.toResponseDTO(batchPurchaseOrder);
    }
}
