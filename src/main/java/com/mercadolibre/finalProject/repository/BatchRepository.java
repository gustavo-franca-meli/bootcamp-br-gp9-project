package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    @Query(value = "SELECT b.* FROM Batch b " +
            "INNER JOIN Sector s ON b.sector_id = s.id " +
            "INNER JOIN Warehouse w ON s.warehouse_id = w.id " +
            "WHERE s.warehouse_id = :warehouseId AND b.product_id = :productId AND b.due_date >= :minimumDueDate", nativeQuery = true)
    public List<Batch> findBatchByWarehouseIdAndProductIdAndMinimumDueDate(Long warehouseId, Long productId, LocalDate minimumDueDate);

    @Query(value = "SELECT b.* FROM Batch b " +
            "INNER JOIN Sector s ON b.sector_id = s.id " +
            "INNER JOIN Warehouse w ON s.warehouse_id = w.id " +
            "WHERE s.warehouse_id = :warehouseId AND b.product_id = :productId AND b.due_date >= :minimumDueDate ORDER BY b.due_date ASC", nativeQuery = true)
    public List<Batch> findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderByDueData(Long warehouseId, Long productId, LocalDate minimumDueDate);

    @Query(value = "SELECT b.* FROM Batch b " +
            "INNER JOIN Sector s ON b.sector_id = s.id " +
            "INNER JOIN Warehouse w ON s.warehouse_id = w.id " +
            "WHERE s.warehouse_id = :warehouseId AND b.product_id = :productId AND b.due_date >= :minimumDueDate ORDER BY b.current_quantity ASC", nativeQuery = true)
    public List<Batch> findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderByCurrentQuantity(Long warehouseId, Long productId, LocalDate minimumDueDate);

}