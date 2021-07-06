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

    @Query("SELECT A FROM Batch A INNER JOIN Sector B ON (A.sector=B.id) " +
            "INNER JOIN Warehouse C ON (B.warehouse=C.id) " +
            "WHERE C.country=:countryId AND A.product=:productId")
    List<Batch> findByProductAndCountry (Long productId, Long countryId);

    @Query("SELECT A FROM Batch A INNER JOIN Sector B ON (A.sector=B.id) " +
            "INNER JOIN Warehouse C ON (B.warehouse=C.id) " +
            "WHERE C.country=:countryId AND A.product=:productId AND A.dueDate >=:date " +
            "ORDER BY A.dueDate ASC")
    List<Batch> findByProductCountryAndDate (Long productId, Long countryId, LocalDate date);

    @Query("SELECT A.product " +
            "FROM Batch A " +
            "INNER JOIN Sector B ON (A.sector=B.id) " +
            "INNER JOIN Warehouse C ON (B.warehouse=C.id) " +
            "WHERE C.country=:countryId AND A.dueDate >=:date " +
            "GROUP BY A.product")
    List<Product> findProductsByCountryAndDate (Long countryId, LocalDate date);

    @Query("SELECT A.product " +
            "FROM Batch A " +
            "INNER JOIN Sector B ON (A.sector=B.id) " +
            "INNER JOIN Warehouse C ON (B.warehouse=C.id) " +
            "WHERE C.country=:countryId " +
            "GROUP BY A.product")
    List<Product> findProductsByCountry(Long countryId);

    @Query("SELECT sum(A.currentQuantity) AS quantity " +
            "FROM Batch A " +
            "INNER JOIN Sector B ON (A.sector=B.id) " +
            "INNER JOIN Warehouse C ON (B.warehouse=C.id) " +
            "WHERE C.country=:countryId AND A.dueDate >=:date AND A.product=:productId " +
            "GROUP BY A.product ")
    Integer getProductQuantityByCountryAndDate (Long productId, Long countryId, LocalDate date);


}
