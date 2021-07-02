package com.mercadolibre.finalProject.util;

import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.enums.SectorType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface TestUtils {

    static Optional<Warehouse> getOptionalWarehouseValid() {
        var warehouse = getWarehouseValid();
        return Optional.of(warehouse);
    }

    static Optional<Warehouse> getOptionalWarehouseInvalid() {
        return Optional.ofNullable(null);
    }

    static Warehouse getWarehouseValid() {
        var sectors = getListSectorsValid();
        var representative = getRepresentativeValid();
        var warehouse = new Warehouse("Casa central da Argentina", sectors, representative);
        warehouse.setId(1L);
        return warehouse;
    }

    static List<Sector> getListSectorsValid() {
        return Arrays.asList(getSectorValid());
    }

    static Sector getSectorValid() {
        var types = convertSectorsToSetInteger();
        var sector = new Sector();
        sector.setId(1l);
        sector.setTypes(types);
        sector.setWarehouse(null);
        sector.setCurrentQuantityBatches(1.0);
        sector.setMaxQuantityBatches(10.0);

        return sector;
    }

    static Set<Integer> convertSectorsToSetInteger() {
        return Arrays.stream(SectorType.values()).map(SectorType::getCod).collect(Collectors.toSet());
    }

    static Representative getRepresentativeValid() {
        var representative = new Representative("Leonardo", null);
        representative.setId(1l);
        return representative;
    }

    static Seller getSellerValid() {
        var products = Arrays.asList(getProductValid());
        var seller = new Seller("Leonardo", products);
        seller.setId(1L);
        return seller;
    }
    static Product getProductValid() {
        return new Product("Pizza", null);
    }

    static Batch getBatchValid() {
        var product = getProductValid();
        var sector = getSectorValid();
        return new Batch(1L, product, sector, 0.0f, 0.0f, 10, 5, LocalDate.now(), LocalDateTime.now(), LocalDate.now());
    }

    static Order getOrderValid() {
        var representative = getRepresentativeValid();
        var batchs = Arrays.asList(getBatchValid());
        return new Order(LocalDate.now(), representative, batchs);
    }

}
