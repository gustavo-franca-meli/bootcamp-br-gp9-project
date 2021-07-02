package com.mercadolibre.finalProject.util;

import com.mercadolibre.finalProject.model.Representative;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.model.Warehouse;
import com.mercadolibre.finalProject.model.enums.SectorType;

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
        return new Warehouse("Casa central da Argentina", sectors, representative);
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

}
