package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    public List<Batch> getBatchesOfProductByDate(Long sectorId, Long productId);

}
