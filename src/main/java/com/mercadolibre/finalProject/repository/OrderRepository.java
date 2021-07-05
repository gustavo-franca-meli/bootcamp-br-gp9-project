package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<InboundOrder, Long> {
}
