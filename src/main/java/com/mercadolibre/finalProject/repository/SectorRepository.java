package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectorRepository extends JpaRepository<Sector,Long> {
    Optional<Sector> findById (Long sectorId);
}
