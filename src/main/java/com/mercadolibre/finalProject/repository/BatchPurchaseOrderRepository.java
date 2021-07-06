package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.BatchPurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchPurchaseOrderRepository extends JpaRepository<BatchPurchaseOrder,Long> {
}
