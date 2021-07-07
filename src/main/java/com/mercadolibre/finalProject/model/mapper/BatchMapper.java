package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.BatchRequestCreateDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.BatchRequestUpdateDTO;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.InboundOrder;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Sector;

import java.util.List;

public interface BatchMapper {

    static Batch toModel(BatchDTO batchDTO, Long sectorId, Long orderId) {
        return new Batch(
                batchDTO.getId(),
                new Product(batchDTO.getProductId()),
                new Sector(sectorId),
                new InboundOrder(orderId),
                batchDTO.getCurrentTemperature(),
                batchDTO.getMinimumTemperature(),
                batchDTO.getInitialQuantity(),
                batchDTO.getCurrentQuantity(),
                batchDTO.getManufacturingDate(),
                batchDTO.getManufacturingTime(),
                batchDTO.getDueDate()
        );
    }

     static BatchDTO toDTO(Batch batch) {
        return new BatchDTO(
                batch.getId(),
                batch.getProduct().getId(),
                batch.getCurrentTemperature(),
                batch.getMinimumTemperature(),
                batch.getInitialQuantity(),
                batch.getCurrentQuantity(),
                batch.getManufacturingDate(),
                batch.getManufacturingTime(),
                batch.getDueDate()
        );
    }

    static BatchDTO toDTO(BatchRequestCreateDTO batchStock) {
        return new BatchDTO(
                batchStock.getProductId(),
                batchStock.getCurrentTemperature(),
                batchStock.getMinimumTemperature(),
                batchStock.getInitialQuantity(),
                batchStock.getCurrentQuantity(),
                batchStock.getManufacturingDate(),
                batchStock.getManufacturingTime(),
                batchStock.getDueDate()
        );
    }
    static BatchDTO toDTO(BatchRequestUpdateDTO batchStock) {
        return new BatchDTO(
                batchStock.getId(),
                batchStock.getProductId(),
                batchStock.getCurrentTemperature(),
                batchStock.getMinimumTemperature(),
                batchStock.getInitialQuantity(),
                batchStock.getCurrentQuantity(),
                batchStock.getManufacturingDate(),
                batchStock.getManufacturingTime(),
                batchStock.getDueDate()
        );
    }
}
