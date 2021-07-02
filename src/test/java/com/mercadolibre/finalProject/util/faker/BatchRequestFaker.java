package com.mercadolibre.finalProject.util.faker;

import com.mercadolibre.finalProject.dtos.BatchDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class BatchRequestFaker  {

    static  public BatchDTO validRequest(){
        var batch = new BatchDTO();
        batch.setId(1L);
        batch.setProductId(2L);
        batch.setInitialQuantity(10);
        batch.setCurrentQuantity(10);
        batch.setCurrentTemperature(25.5f);
        batch.setMinimumTemperature(-5.5f);
        batch.setManufacturingDate(LocalDate.now());
        batch.setManufacturingTime(LocalDateTime.now());
        batch.setDueDate(LocalDate.now());
        return batch;
    }



}
