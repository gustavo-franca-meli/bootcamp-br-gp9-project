package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    @Query("FROM Batch a WHERE a.sector=:sectorId AND a.product=:productId") // AND a.manufacturingDate:=date
    List<Batch> getBatchesOfProductByDate(Long sectorId, Long productId); //, LocalDate date
}
