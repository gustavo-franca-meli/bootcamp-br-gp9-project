package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.dtos.ProductStatusWarehouseDTO;
import com.mercadolibre.finalProject.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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


    @Query(value = "select a.product_id, " +
            "sum(CASE WHEN a.due_date <=:date1 THEN 1 ELSE 0 END) AS count_batches_less_3_weeks, " +
            "sum(CASE WHEN a.due_date <=:date1 THEN a.current_quantity ELSE 0 END) AS quantity_less_3_weeks, " +
            "sum(CASE WHEN a.due_date >:date1 AND a.due_date <=:date2 THEN 1 ELSE 0 END) AS count_batches_more_3_weeks, " +
            "sum(CASE WHEN a.due_date >:date1 AND a.due_date <=:date2 THEN a.current_quantity ELSE 0 END) AS quantity_more_3_weeks, " +
            "sum(CASE WHEN a.due_date >:date2 THEN 1 ELSE 0 END) AS count_batches_more_2_months, " +
            "sum(CASE WHEN a.due_date >:date2 THEN a.current_quantity ELSE 0 END) AS quantity_more_2_months " +
            "from batch a inner join sector b on (a.sector_id=b.id) inner join warehouse c on (b.warehouse_id=c.id) " +
            "where c.representative_id =:representativeId group by a.product_id order by sum(CASE WHEN a.due_date <= '2021-8-26' THEN 1 ELSE 0 END) DESC", nativeQuery = true)
    List<IProductStatusWarehouseDTO> getWarehouseStatus (Long representativeId, LocalDate date1, LocalDate date2);

    public static interface ISumOfProductStockDTO {
        String getWarehouse_id();
        String getQuantity();
    }

    public static interface IProductStatusWarehouseDTO {
        String getProduct_id();
        String getCount_batches_less_3_weeks();
        String getQuantity_less_3_weeks();
        String getCount_batches_more_3_weeks();
        String getQuantity_more_3_weeks();
        String getCount_batches_more_2_months();
        String getQuantity_more_2_months();
    }
}
