package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Sector;

public class BatchMapper {


    public static Batch toModel(BatchDTO bathDto, Long sectorId) {
      return new Batch(
              bathDto.getId(),
              new Product(bathDto.getProductId()),
              new Sector(sectorId),
              bathDto.getCurrentTemperature(),
              bathDto.getMinimumTemperature(),
              bathDto.getInitialQuantity(),
              bathDto.getCurrentQuantity(),
              bathDto.getManufacturingDate(),
              bathDto.getManufacturingTime(),
              bathDto.getDueDate()
      );


    }

    public static BatchDTO toDto(Batch bath) {
        return new BatchDTO(
                bath.getId(),
                bath.getProduct().getId(),
                bath.getCurrentTemperature(),
                bath.getMinimumTemperature(),
                bath.getInitialQuantity(),
                bath.getCurrentQuantity(),
                bath.getManufacturingDate(),
                bath.getManufacturingTime(),
                bath.getDueDate()
        );
    }
}
