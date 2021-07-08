package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.BatchRequestCreateDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.BatchRequestUpdateDTO;
import com.mercadolibre.finalProject.dtos.response.BatchIdentificationResponseDTO;
import com.mercadolibre.finalProject.dtos.response.BatchSectorResponseDTO;
import com.mercadolibre.finalProject.dtos.response.BatchStockResponseDTO;
import com.mercadolibre.finalProject.dtos.response.SectorBatchResponseDTO;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.InboundOrder;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Sector;

import java.util.List;
import java.util.stream.Collectors;

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

    static BatchSectorResponseDTO toSectorResponseDTO(Batch batch) {
        return new BatchSectorResponseDTO(
                batch.getId(),
                batch.getProduct().getId(),
                batch.getProduct().getProductType(),
                batch.getDueDate(),
                batch.getCurrentQuantity()
        );
    }

    static List<BatchSectorResponseDTO> toListSectorResponseDTO(List<Batch> batches) {
        return batches.stream().map(BatchMapper::toSectorResponseDTO).collect(Collectors.toList());
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

    static SectorBatchResponseDTO toSectorBatchResponseDTO(List<Batch> batches) {
        var sector = batches.get(0).getSector();
        var batchIdentification = assembleBatchIdentificationResponseDTOOf(sector.getId(), sector.getWareHouseId());
        var product = batches.get(0).getProduct();
        var batchStock = toListBatchStockResponseDTO(batches);
        return new SectorBatchResponseDTO(batchIdentification, product.getId(), batchStock);
    }

    static BatchIdentificationResponseDTO assembleBatchIdentificationResponseDTOOf(Long sectorId, Long warehouseId) {
        return new BatchIdentificationResponseDTO(sectorId, warehouseId);
    }

    static List<BatchStockResponseDTO> toListBatchStockResponseDTO(List<Batch> batches) {
        return batches.stream().map(BatchMapper::toBatchStockResponseDTO).collect(Collectors.toList());
    }

    static BatchStockResponseDTO toBatchStockResponseDTO(Batch batch) {
        return new BatchStockResponseDTO(batch.getId(), batch.getCurrentQuantity(), batch.getDueDate());
    }

}
