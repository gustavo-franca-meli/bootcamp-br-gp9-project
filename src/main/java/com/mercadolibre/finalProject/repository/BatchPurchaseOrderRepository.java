package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.BatchPurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BatchPurchaseOrderRepository extends JpaRepository<BatchPurchaseOrder,Long> {
    @Query(value = "SELECT A.* FROM batch_purchase_order A WHERE A.purchase_batch_order_id=:purchaseBatchId", nativeQuery = true)
    List<BatchPurchaseOrder> findBatchesOfProductPurchaseOrder(Long purchaseBatchId);
}
