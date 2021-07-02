package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,String> {
}
