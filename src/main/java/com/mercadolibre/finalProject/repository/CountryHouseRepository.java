package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.CountryHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CountryHouseRepository extends JpaRepository<CountryHouse, UUID> {
    CountryHouse findByCountry(String country);
}
