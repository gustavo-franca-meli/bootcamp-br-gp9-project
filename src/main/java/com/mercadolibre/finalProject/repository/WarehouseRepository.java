package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Query(value= " SELECT sector.warehouse_id as warehouse_id, SUM(current_quantity) as quantity FROM batch " +
            " INNER JOIN sector ON batch.sector_id = sector.id " +
            " INNER JOIN warehouse ON sector.warehouse_id = warehouse.id " +
            " WHERE batch.sector_id = sector.id " +
            " AND product_id = :productId " +
            " AND warehouse.country_id = :countryId " +
            " GROUP BY warehouse_id ", nativeQuery = true)
    public List<ISumOfProductStockDTO> getSumOfProductsByCountry (@Param("productId") Long productId , @Param("countryId") Long countryId );

    @Query(value= " SELECT sector.warehouse_id as warehouse_id, SUM(current_quantity) as quantity FROM batch " +
            " INNER JOIN sector ON batch.sector_id = sector.id " +
            " INNER JOIN warehouse ON sector.warehouse_id = warehouse.id " +
            " WHERE batch.sector_id = sector.id " +
            " AND product_id = :productId " +
            " GROUP BY warehouse_id ", nativeQuery = true)
    public List<ISumOfProductStockDTO> getSumOfProductStockInAllWarehouses (@Param("productId") Long productId);


    public static interface ISumOfProductStockDTO {
        String getWarehouse_id();
        String getQuantity();
    }

}
