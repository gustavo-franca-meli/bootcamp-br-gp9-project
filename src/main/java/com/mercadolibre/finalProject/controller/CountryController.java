package com.mercadolibre.finalProject.controller;


import com.mercadolibre.finalProject.dtos.response.CountryResponseDTO;
import com.mercadolibre.finalProject.dtos.request.CountryRequestDTO;
import com.mercadolibre.finalProject.service.ICountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping(path = "/api/v1/countries")
@RestController
public class CountryController {
    private ICountryService countryService;

    public CountryController(ICountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public ResponseEntity<CountryResponseDTO> create(@Validated @RequestBody CountryRequestDTO newCountryHouse) {
        CountryResponseDTO countryResponseDTO = countryService.create(newCountryHouse);

        return new ResponseEntity(countryResponseDTO, HttpStatus.CREATED);
    }
}
