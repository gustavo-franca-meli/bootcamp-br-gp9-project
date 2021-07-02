package com.mercadolibre.finalProject.util.faker;

import com.mercadolibre.finalProject.dtos.BatchDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class BathRequestFaker  {


    static  public BatchDTO validRequest(){
        var bath = new BatchDTO();
        bath.setId(1L);
        bath.setProductId(1L);
        bath.setInitialQuantity(10);
        bath.setCurrentQuantity(10);
        bath.setCurrentTemperature(25.5f);
        bath.setMinimumTemperature(-5.5f);
        bath.setManufacturingDate(LocalDate.now());
        bath.setManufacturingTime(LocalDateTime.now());
        bath.setDueDate(LocalDate.now());
        return bath;
    }



}
