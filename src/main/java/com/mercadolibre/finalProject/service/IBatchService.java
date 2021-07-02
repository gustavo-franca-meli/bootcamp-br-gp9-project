package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.exceptions.CreateBathStockException;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IBatchService {
    List<Batch> create(@NotNull  List<BatchDTO> batchStock, Sector sector) throws CreateBathStockException;
}
