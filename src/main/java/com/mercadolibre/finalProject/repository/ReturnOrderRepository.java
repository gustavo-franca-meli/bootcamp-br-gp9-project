package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.ReturnOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnOrderRepository extends JpaRepository<ReturnOrder, Long> {
}
