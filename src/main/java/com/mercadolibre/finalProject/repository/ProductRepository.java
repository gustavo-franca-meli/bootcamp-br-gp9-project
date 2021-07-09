package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.dtos.response.SumOfProductStockDTO;
import com.mercadolibre.finalProject.model.Account;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = " SELECT sector.warehouse_id as warehouse_id, SUM(current_quantity) as quantity FROM batch, sector " +
            " WHERE batch.sector_id = sector.id AND product_id = :productId " +
            " GROUP BY warehouse_id ", nativeQuery = true)
    public List<ISumOfProductStockDTO> getSumOfProductStockInAllWarehouses (@Param("productId") Long productId);

    public static interface ISumOfProductStockDTO {
        String getWarehouse_id();
        String getQuantity();
    }
}
