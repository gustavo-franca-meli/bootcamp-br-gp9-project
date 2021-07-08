package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.ProductBatchesPurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductBatchesPurchaseOrderRepository extends JpaRepository<ProductBatchesPurchaseOrder, Long> {
    @Query(value = "SELECT A.* FROM product_batch_purchase_order A WHERE A.purchase_order_id=:purchaseOrderId", nativeQuery = true)
    List<ProductBatchesPurchaseOrder> findProductBatchesPurchaseOrder(Long purchaseOrderId);
}
