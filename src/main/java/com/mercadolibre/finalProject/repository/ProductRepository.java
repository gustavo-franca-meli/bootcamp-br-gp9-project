package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query( value = " SELECT product.name AS product_name, warehouse.name AS warehouse_name, SUM(batch.current_quantity) AS quantity_in_stock, product.price AS unitary_price, ROUND(product.price * SUM(batch.current_quantity),2) AS total_assets_value " +
            " FROM product " +
            " INNER JOIN batch  ON product.id = batch.product_id " +
            " INNER JOIN sector on batch.sector_id = sector.id " +
            " INNER JOIN warehouse on warehouse.id = sector.warehouse_id " +
            " GROUP BY product.name, warehouse.name, product.price ", nativeQuery = true)
    public List<IProductInventory> getProductInventory();

    public static interface ISumOfProductStockDTO {
        String getWarehouse_id();
        String getQuantity();
    }

    public static interface IProductInventory {
        String getProduct_name();
        String getWarehouse_name();
        String getQuantity_in_stock();
        String getUnitary_price();
        String getTotal_assets_value();
    }

//    # nome do produto, nome do warehouse, quantidade em estoque, valor unitário, valor total
}
