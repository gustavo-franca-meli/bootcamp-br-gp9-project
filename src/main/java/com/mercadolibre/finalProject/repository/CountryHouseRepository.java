package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.CountryHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryHouseRepository extends JpaRepository<CountryHouse, Long> {
    CountryHouse findByCountry(String country);
}
