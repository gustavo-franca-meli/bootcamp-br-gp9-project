package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    Optional<Sector> findById (Long sectorId);
}
