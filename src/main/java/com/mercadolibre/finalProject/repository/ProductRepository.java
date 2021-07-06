package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query("SELECT A FROM Product A INNER JOIN Sector B ON (A.sector=B.id) " +
//            "INNER JOIN Warehouse C ON (B.warehouse=C.id) " +
//            "WHERE C.country=:countryId AND A.product=:productId AND A.dueDate >=:date " +
//            "ORDER BY A.dueDate ASC")
//    List<Product> findByCountryAndDate (Long productId, Long countryId, LocalDate date);
//
//    @Query("SELECT A.product, sum(A.currentQuantity) AS quantity FROM Batch A " +
//            "INNER JOIN Sector B ON (A.sector=B.id) " +
//            "INNER JOIN Warehouse C ON (B.warehouse=C.id) " +
//            "WHERE C.country=:countryId AND A.product=:productId AND A.dueDate >=:date " +
//            "ORDER BY A.dueDate ASC" +
//            "GROUP BY A.product")
//    List<Product> findByCountryAndDate (Long productId, Long countryId, LocalDate date);

}
