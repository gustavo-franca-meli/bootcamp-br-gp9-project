package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.BatchDto;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IBathService {
    List<Batch> create(@NotNull  List<BatchDto> batchStock, Sector sector);
}
