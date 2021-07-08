package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {
}
