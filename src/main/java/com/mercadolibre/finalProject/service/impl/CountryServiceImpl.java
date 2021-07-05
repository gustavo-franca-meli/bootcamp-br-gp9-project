package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.request.CountryRequestDTO;
import com.mercadolibre.finalProject.dtos.response.CountryResponseDTO;
import com.mercadolibre.finalProject.exceptions.NotFoundException;
import com.mercadolibre.finalProject.model.Country;
import com.mercadolibre.finalProject.model.mapper.CountryMapper;
import com.mercadolibre.finalProject.repository.CountryRepository;
import com.mercadolibre.finalProject.service.ICountryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CountryServiceImpl implements ICountryService {

    private CountryRepository countryRepository;

    private ModelMapper modelMapper;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CountryResponseDTO create(CountryRequestDTO countryRequest) {
        Country country = null;
        if (countryRepository.findByName(countryRequest.getName()) == null) {
            Country newCountry = modelMapper.map(countryRequest, Country.class);
            country = countryRepository.save(newCountry);
        }

        return CountryMapper.toResponseDTO(country);
    }

    @Override
    @Transactional
    public CountryResponseDTO update(Long id, CountryRequestDTO countryRequest) {
        if (countryRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Country not found");
        }

        Country newCountry = modelMapper.map(countryRequest, Country.class);
        countryRepository.save(newCountry);

        return CountryMapper.toResponseDTO(newCountry);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Country> opt = countryRepository.findById(id);
        if (opt.isEmpty()) {
            throw new NotFoundException("Country not found");
        }

        countryRepository.deleteById(id);
    }

    @Override
    public CountryResponseDTO findById(Long id) {
        Optional<Country> opt = countryRepository.findById(id);
        if (opt.isEmpty()) {
            throw new NotFoundException("Country not found");
        }

        return CountryMapper.toResponseDTO(opt.get());
    }

    @Override
    public List<CountryResponseDTO> findAll() {
        var list = countryRepository.findAll();
        if (list == null || list.isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }

        return CountryMapper.listToResponseDTO(list);
    }

    @Override
    public CountryResponseDTO findByCountry(String country) {
        Country countryHouse = countryRepository.findByName(country);
        if (countryHouse == null) {
            throw new NotFoundException("Country not found");
        }

        return CountryMapper.toResponseDTO(countryHouse);
    }
}
