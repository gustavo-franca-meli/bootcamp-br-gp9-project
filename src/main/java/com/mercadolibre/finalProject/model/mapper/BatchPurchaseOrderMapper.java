package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.BatchPurchaseOrderDTO;
import com.mercadolibre.finalProject.dtos.response.BatchPurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.BatchPurchaseOrder;
import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;

import java.util.List;
import java.util.stream.Collectors;

public interface BatchPurchaseOrderMapper {

    static BatchPurchaseOrderResponseDTO toResponseDTO(BatchPurchaseOrder batchPurchaseOrder) {
        return new BatchPurchaseOrderResponseDTO(
                batchPurchaseOrder.getId(),
                batchPurchaseOrder.getBatch().getId(),
                batchPurchaseOrder.getQuantity(),
                batchPurchaseOrder.getBatch().getManufacturingDate(),
                batchPurchaseOrder.getBatch().getManufacturingTime(),
                batchPurchaseOrder.getBatch().getDueDate()
        );
    }

    static BatchPurchaseOrderResponseDTO toResponseDTO(Batch batch, Integer quantity) {
        return new BatchPurchaseOrderResponseDTO(
                null,
                batch.getId(),
                quantity,
                batch.getManufacturingDate(),
                batch.getManufacturingTime(),
                batch.getDueDate()
        );
    }

    static BatchPurchaseOrderDTO toDTO(BatchDTO batchDTO, Integer quantity) {
        return new BatchPurchaseOrderDTO(
                null,
                quantity,
                batchDTO
        );
    }

    static BatchPurchaseOrder toModel(Batch batch, Integer quantity) {
        return new BatchPurchaseOrder(quantity, batch);
    }

    static BatchPurchaseOrder toModel(Batch batch, Integer quantity, ProductBatchesPurchaseOrder productBatchesPurchaseOrder) {
        return new BatchPurchaseOrder(quantity, batch, productBatchesPurchaseOrder);
    }

    static List<BatchPurchaseOrderResponseDTO> toListResponseDTO(List<BatchPurchaseOrder> batches) {
        return batches.stream().map(BatchPurchaseOrderMapper::toResponseDTO).filter(b -> b.getQuantity() > 0).collect(Collectors.toList());
    }
}
