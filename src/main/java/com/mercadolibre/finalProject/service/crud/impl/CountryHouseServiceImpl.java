package com.mercadolibre.finalProject.service.crud.impl;

import com.mercadolibre.finalProject.dtos.CountryHouseDTO;
import com.mercadolibre.finalProject.model.Country;
import com.mercadolibre.finalProject.repository.CountryHouseRepository;
import com.mercadolibre.finalProject.service.crud.ICountryHouseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryHouseServiceImpl implements ICountryHouseService {

    private CountryHouseRepository  countryHouseRepository;

    private ModelMapper modelMapper;

    public CountryHouseServiceImpl(CountryHouseRepository countryHouseRepository, ModelMapper modelMapper) {
        this.countryHouseRepository = countryHouseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CountryHouseDTO create(CountryHouseDTO countryHouseDTO) {
        if (countryHouseRepository.findByName(countryHouseDTO.getCountry())==null){
            Country newCountry = modelMapper.map(countryHouseDTO, Country.class);
            countryHouseRepository.save(newCountry);
        }else{
            countryHouseDTO=null;
        }

        return countryHouseDTO;
    }

    @Override
    @Transactional
    public CountryHouseDTO update(CountryHouseDTO countryHouseDTO) {
        Country newCountry = modelMapper.map(countryHouseDTO, Country.class);
        countryHouseRepository.save(newCountry);
        return countryHouseDTO;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Country> opt = countryHouseRepository.findById(id);
        if (!opt.isPresent()) {
            throw new NoSuchElementException("No existe empleado con el id: " + id);
        }
        countryHouseRepository.deleteById(id);
    }

    @Override
    public CountryHouseDTO findById(Long id) {
        Optional<Country> opt = countryHouseRepository.findById(id);

        if (!opt.isPresent()) {
            throw new NoSuchElementException("No existe empleado con el id: " + id);
        }
        return modelMapper.map(opt.get(), CountryHouseDTO.class);
    }

    @Override
    public List<CountryHouseDTO> findAll() {
        List<CountryHouseDTO> countryHousesDTO = countryHouseRepository.findAll()
                .stream()
                .map(country -> modelMapper.map(country, CountryHouseDTO.class))
                .collect(Collectors.toList());
        return countryHousesDTO;
    }

    @Override
    public CountryHouseDTO findByCountry(String country) {
        Country contryHouse = countryHouseRepository.findByName(country);
        return modelMapper.map(contryHouse, CountryHouseDTO.class);
    }
}
