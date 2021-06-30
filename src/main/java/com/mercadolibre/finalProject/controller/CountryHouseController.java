package com.mercadolibre.finalProject.controller;


import com.mercadolibre.finalProject.dtos.CountryHouseDTO;
import com.mercadolibre.finalProject.dtos.response.CountryHouseResponseDTO;
import com.mercadolibre.finalProject.service.crud.ICountryHouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping(path = "/api/v1/countries")
@RestController
public class CountryHouseController {
    private ICountryHouseService countryHouseService;

    public CountryHouseController(ICountryHouseService countryHouseService) {
        this.countryHouseService = countryHouseService;
    }

    /**================================
     * Crea una casa pais
     * ================================
     * Crea la casa país.
     */
    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public ResponseEntity<CountryHouseResponseDTO> create(@RequestHeader(value="Authorization") String token, @Validated @RequestBody CountryHouseDTO newCountryHouse) {
        CountryHouseDTO countryHouseDTO = countryHouseService.create(newCountryHouse);

        CountryHouseResponseDTO response = new CountryHouseResponseDTO();

        if (countryHouseDTO!=null){
            response.setCountryHouseDTO(countryHouseDTO);
            response.setMessage("CREADO");
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(countryHouseDTO.getId()).toUri();
            return new ResponseEntity<CountryHouseResponseDTO>(response, HttpStatus.CREATED).created(location).body(response);
        }else{
            response.setCountryHouseDTO(newCountryHouse);
            response.setMessage("YA EXISTE");
            return new ResponseEntity<CountryHouseResponseDTO>(response, HttpStatus.BAD_REQUEST);
        }

    }
}
