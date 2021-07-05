package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CountryHouseRepository extends JpaRepository<Country, Long> {
    Country findByName(String name);
}
