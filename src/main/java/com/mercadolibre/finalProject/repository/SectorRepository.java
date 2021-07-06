package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    @Query(value = "SELECT count(b.id) FROM batch b inner join sector On sector.id = b.sector_id where sector.id = :sectorId",nativeQuery = true)
    Integer countBatchesIn(@Param("sectorId") Long sectorId);
}
