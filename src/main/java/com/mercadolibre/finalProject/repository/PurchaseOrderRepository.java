package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {
    @Query(value = "SELECT A.* FROM purchase_order A WHERE A.buyer_id=:id",nativeQuery = true)
    List<PurchaseOrder> findByBuyerId(Long id);
}
