package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    @Query(value = "SELECT b.* FROM Batch b " +
            "INNER JOIN Sector s ON b.sector_id = s.id " +
            "INNER JOIN Warehouse w ON s.warehouse_id = w.id " +
            "WHERE s.warehouse_id = :warehouseId AND b.product_id = :productId AND b.due_date >= :minimumDueDate", nativeQuery = true)
    List<Batch> findBatchByWarehouseIdAndProductIdAndMinimumDueDate(Long warehouseId, Long productId, LocalDate minimumDueDate);

    @Query(value = "SELECT b.* FROM Batch b " +
            "INNER JOIN Sector s ON b.sector_id = s.id " +
            "INNER JOIN Warehouse w ON s.warehouse_id = w.id " +
            "WHERE s.warehouse_id = :warehouseId AND b.product_id = :productId AND b.due_date >= :minimumDueDate ORDER BY b.due_date ASC", nativeQuery = true)
    List<Batch> findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderByDueDate(Long warehouseId, Long productId, LocalDate minimumDueDate);

    @Query(value = "SELECT b.* FROM Batch b " +
            "INNER JOIN Sector s ON b.sector_id = s.id " +
            "INNER JOIN Warehouse w ON s.warehouse_id = w.id " +
            "WHERE s.warehouse_id = :warehouseId AND b.product_id = :productId AND b.due_date >= :minimumDueDate ORDER BY b.current_quantity ASC", nativeQuery = true)
    List<Batch> findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderByCurrentQuantity(Long warehouseId, Long productId, LocalDate minimumDueDate);

    @Query(value = "SELECT b.* FROM Batch b"
            + " INNER JOIN Sector s ON b.sector_id = :sectorId"
            + " WHERE b.due_date >= :minimumDueDate"
            + " ORDER BY b.due_date ASC", nativeQuery = true)
    List<Batch> findBatchesBySectorId(Long sectorId, LocalDate minimumDueDate);

    @Query(value = "SELECT b.* FROM Batch b"
            + " INNER JOIN Product p ON p.id = b.product_id AND p.product_type = :productTypeCode"
            + " WHERE b.due_date >= :minimumDueDate"
            + " ORDER BY b.due_date ASC", nativeQuery = true)
    List<Batch> findBatchesByProductType(Integer productTypeCode, LocalDate minimumDueDate);
}