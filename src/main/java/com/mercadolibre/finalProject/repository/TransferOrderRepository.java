package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.TransferOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferOrderRepository extends JpaRepository<TransferOrder,Long> {
}
