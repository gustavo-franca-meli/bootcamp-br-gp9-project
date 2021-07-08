package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Product;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    @Query(value = "SELECT A.* FROM Batch A INNER JOIN Sector B ON (A.sector_id=B.id) " +
            "INNER JOIN Warehouse C ON (B.warehouse_id=C.id) " +
            "WHERE C.country_id=:countryId AND A.product_id=:productId", nativeQuery = true)
    List<Batch> findByProductAndCountry (Long productId, Long countryId);

    @Query(value = "SELECT A.* FROM Batch A INNER JOIN Sector B ON (A.sector_id=B.id) " +
            "INNER JOIN Warehouse C ON (B.warehouse_id=C.id) " +
            "WHERE C.country_id=:countryId AND A.product_id=:productId AND A.due_date >=:date " +
            "ORDER BY A.due_date ASC", nativeQuery = true)
    List<Batch> findByProductCountryAndDate (Long productId, Long countryId, LocalDate date);

    @Query(value = "SELECT A.product " +
            "FROM Batch A " +
            "INNER JOIN Sector C ON (A.sector_id=C.id) " +
            "INNER JOIN Warehouse C ON (C.warehouse_id=D.id) " +
            "WHERE D.country_id=:countryId AND A.due_date >=:date " +
            "GROUP BY A.product_id", nativeQuery = true)
    List<Product> findProductsByCountryAndDate (Long countryId, LocalDate date);

    @Query(value = "SELECT B.* " +
            "FROM Batch A " +
            "INNER JOIN Product B ON (A.product_id=B.id) " +
            "INNER JOIN Sector C ON (A.sector_id=C.id) " +
            "INNER JOIN Warehouse D ON (C.warehouse_id=D.id) " +
            "WHERE D.country=:countryId", nativeQuery = true)
    List<Product> findProductsByCountry(Long countryId);

    @Query(value = "SELECT sum(A.current_quantity) AS quantity " +
            "FROM Batch A " +
            "INNER JOIN Sector B ON (A.sector_id=B.id) " +
            "INNER JOIN Warehouse C ON (B.warehouse_id=C.id) " +
            "WHERE C.country_id=:countryId AND A.due_date >=:date AND A.product_id=:productId " +
            "GROUP BY A.product_id", nativeQuery = true)
    Integer getProductQuantityByCountryAndDate (Long productId, Long countryId, LocalDate date);


}
