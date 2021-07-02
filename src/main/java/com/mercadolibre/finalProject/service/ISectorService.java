package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.BatchDto;
import com.mercadolibre.finalProject.dtos.SectionDto;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.enums.SectorType;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ISectorService {
    Boolean exist(String sectorId);
    Sector findById(String code);
}
