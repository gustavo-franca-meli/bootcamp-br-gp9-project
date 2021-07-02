package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BathRepository extends JpaRepository<Batch, Long> {
}
