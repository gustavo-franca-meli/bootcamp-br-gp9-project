package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.RepresentativeResponseDTO;
import com.mercadolibre.finalProject.model.Representative;

public interface RepresentativeMapper {

    static RepresentativeResponseDTO toResponseDTO(Representative representative) {
        return new RepresentativeResponseDTO(representative.getId(), representative.getName());
    }
}
