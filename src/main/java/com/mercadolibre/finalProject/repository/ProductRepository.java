package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.dtos.response.SumOfProductStockDTO;
import com.mercadolibre.finalProject.model.Account;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = " SELECT sector.warehouse_id as warehouse_id, SUM(current_quantity) as quantity FROM batch, sector " +
            " WHERE batch.sector_id = sector.id AND product_id = :productId " +
            " GROUP BY warehouse_id ", nativeQuery = true)
    public List<ISumOfProductStockDTO> getSumOfProductStockInAllWarehouses (@Param("productId") Long productId);

    @Query(value= " SELECT id from warehouse where country_id = :desiredCountry ", nativeQuery = true)
    public List<Long> getWarehousesByCountry(@Param("desiredCountry") Long countryId);

    public static interface ISumOfProductStockDTO {
        String getWarehouse_id();
        String getQuantity();
    }
}
