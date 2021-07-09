package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.exceptions.NotFoundException;
import com.mercadolibre.finalProject.model.BatchPurchaseOrder;
import com.mercadolibre.finalProject.model.mapper.BatchPurchaseOrderMapper;
import com.mercadolibre.finalProject.repository.BatchPurchaseOrderRepository;
import com.mercadolibre.finalProject.service.IBatchPurchaseOrderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BatchPurchaseOrderServiceImpl implements IBatchPurchaseOrderService {

    private BatchPurchaseOrderRepository repository;

    public BatchPurchaseOrderServiceImpl(BatchPurchaseOrderRepository repository) {
        this.repository = repository;
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
