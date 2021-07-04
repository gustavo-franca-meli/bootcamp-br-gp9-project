package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Warehouse findByRepresentative (String representative);
}
