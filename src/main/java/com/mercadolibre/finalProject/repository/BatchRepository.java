package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Batch;
import org.springframework.data.domain.Sort;
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

    @Query("SELECT b FROM Batch b " +
            "INNER JOIN Sector s ON b.sector.id = s.id " +
            "INNER JOIN Warehouse w ON s.warehouse.id = w.id " +
            "WHERE s.warehouse.id = :warehouseId AND b.product.id = :productId AND b.dueDate >= :minimumDueDate")
    List<Batch> findBatchByWarehouseIdAndProductIdAndMinimumDueDateOrderBySortField(Long warehouseId, Long productId, LocalDate minimumDueDate, Sort sort);

    @Query(value = "SELECT A.* FROM Batch A INNER JOIN Sector B ON (A.sector_id=B.id) " +
            "INNER JOIN Warehouse C ON (B.warehouse_id=C.id) " +
            "WHERE C.country_id=:countryId AND A.product_id=:productId AND A.due_date >=:date " +
            "ORDER BY A.due_date ASC", nativeQuery = true)
    List<Batch> findByProductCountryAndDate (Long productId, Long countryId, LocalDate date);

    @Query(value = "SELECT sum(A.current_quantity) AS quantity " +
            "FROM Batch A " +
            "INNER JOIN Sector B ON (A.sector_id=B.id) " +
            "INNER JOIN Warehouse C ON (B.warehouse_id=C.id) " +
            "WHERE C.country_id=:countryId AND A.due_date >=:date AND A.product_id=:productId " +
            "GROUP BY A.product_id", nativeQuery = true)
    Integer getProductQuantityByCountryAndDate (Long productId, Long countryId, LocalDate date);
}
