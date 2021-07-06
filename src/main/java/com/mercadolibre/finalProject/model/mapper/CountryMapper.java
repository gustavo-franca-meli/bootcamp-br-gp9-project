package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.CountryResponseDTO;
import com.mercadolibre.finalProject.model.Country;

import java.util.List;
import java.util.stream.Collectors;

public interface CountryMapper {

    static List<CountryResponseDTO> listToResponseDTO(List<Country> countries) {
        return countries.stream().map(c -> toResponseDTO(c)).collect(Collectors.toList());
    }

    static CountryResponseDTO toResponseDTO(Country country) {
        return new CountryResponseDTO(country.getId(), country.getName());
    }

}
