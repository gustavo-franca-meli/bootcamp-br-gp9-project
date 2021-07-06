package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.SectorResponseDTO;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.enums.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface SectorMapper {

    static List<SectorResponseDTO> toListResponseDTO(List<Sector> sectors) {
        if (sectors.isEmpty())
            return new ArrayList<>();
        return sectors.stream().map(SectorMapper::toResponseDTO).collect(Collectors.toList());
    }

    static SectorResponseDTO toResponseDTO(Sector sector) {
        var sectorTypeDescription = ProductType.toEnum(sector.getSectorType()).getDescription();
        return new SectorResponseDTO(sector.getId(), sectorTypeDescription, sector.getBatches().size(), sector.getMaxQuantityBatches());
    }


}
