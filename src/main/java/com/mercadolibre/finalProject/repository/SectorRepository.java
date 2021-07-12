package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    @Query(value = "SELECT count(b.id) FROM Batch b " +
            "INNER JOIN Sector s ON b.sector_id = s.id " +
            "WHERE b.sector_id = :sectorId AND b.current_quantity > 0 AND b.status='IN_STOCK'", nativeQuery = true)
    Integer countBatchesIn(@Param("sectorId") Long sectorId);

}
