package com.mercadolibre.finalProject.util.faker;

import com.mercadolibre.finalProject.dtos.BatchDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class BatchRequestFaker {


    static  public BatchDTO validRequest(){
        var bath = new BatchDTO();
        bath.setId(random());
        bath.setProductId(random());
        bath.setInitialQuantity(10);
        bath.setCurrentQuantity(10);
        bath.setCurrentTemperature(25.5f);
        bath.setMinimumTemperature(-5.5f);
        bath.setManufacturingDate(LocalDate.now());
        bath.setManufacturingTime(LocalDateTime.now());
        bath.setDueDate(LocalDate.now());
        return bath;
    }

    static public Long random(){
        Random rand = new Random(); //instance of random class
        int upperbound = 1000;
        //generate random values from 0-24
        return Integer.valueOf(rand.nextInt(upperbound)).longValue();

    }



}
