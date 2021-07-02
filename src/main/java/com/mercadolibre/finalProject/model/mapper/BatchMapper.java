package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.BatchDto;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Sector;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BatchMapper {


    public static Batch toModel(BatchDto bathDto, String sectorId) {
      return new Batch(
              bathDto.getId().longValue(),
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

    public static BatchDto toDto(Batch bath) {
        return new BatchDto(
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
