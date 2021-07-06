package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.InboundOrder;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Sector;

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

}
