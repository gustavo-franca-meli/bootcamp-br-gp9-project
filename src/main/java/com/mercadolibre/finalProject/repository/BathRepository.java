package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BathRepository extends JpaRepository<Batch,String> {
}
