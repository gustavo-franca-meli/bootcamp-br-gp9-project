package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.request.CountryRequestDTO;
import com.mercadolibre.finalProject.dtos.response.CountryResponseDTO;
import com.mercadolibre.finalProject.service.crud.ICRUD;

public interface ICountryService extends ICRUD<CountryRequestDTO, CountryResponseDTO> {
    CountryResponseDTO findByCountry(String country);
}
