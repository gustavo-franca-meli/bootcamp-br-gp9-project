package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BathRepository extends JpaRepository<Batch, UUID> {
}
