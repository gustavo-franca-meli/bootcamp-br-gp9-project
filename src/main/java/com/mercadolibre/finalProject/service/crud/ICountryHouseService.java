package com.mercadolibre.finalProject.service.crud;

import com.mercadolibre.finalProject.dtos.CountryHouseDTO;

public interface ICountryHouseService extends ICRUD<CountryHouseDTO>{
    CountryHouseDTO findByCountry(String country);
}
