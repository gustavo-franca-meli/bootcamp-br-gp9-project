package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT B.* " +
            "FROM Batch A " +
            "INNER JOIN Product B ON (A.product_id=B.id) " +
            "INNER JOIN Sector C ON (A.sector_id=C.id) " +
            "INNER JOIN Warehouse D ON (C.warehouse_id=D.id) " +
            "WHERE D.country_id=:countryId GROUP BY A.product_id", nativeQuery = true)
    List<Product> findByCountry (Long countryId);

    @Query(value = "SELECT B.* " +
            "FROM Batch A " +
            "INNER JOIN Product B ON (A.product_id=B.id) " +
            "INNER JOIN Sector C ON (A.sector_id=C.id) " +
            "INNER JOIN Warehouse D ON (C.warehouse_id=D.id) " +
            "WHERE D.country_id=:countryId AND B.product_type=:productType " +
            "GROUP BY A.product_id", nativeQuery = true)
    List<Product> findByCountryAndType (Long countryId, Integer productType);
}
