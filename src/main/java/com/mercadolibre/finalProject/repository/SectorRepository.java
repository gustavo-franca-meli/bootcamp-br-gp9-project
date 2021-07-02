package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SectorRepository extends JpaRepository<Sector, UUID> {
    Optional<Sector> findById (Long sectorId);
}
