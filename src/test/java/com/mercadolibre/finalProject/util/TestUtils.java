package com.mercadolibre.finalProject.util;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.SectorDTO;
import com.mercadolibre.finalProject.dtos.response.RepresentativeResponseDTO;
import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.enums.SectorType;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.model.mapper.RepresentativeMapper;
import com.mercadolibre.finalProject.model.mapper.WarehouseMapper;

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

    static RepresentativeResponseDTO getRepresentativeResponseDTOValid() {
        return RepresentativeMapper.toResponseDTO(getRepresentativeValid());
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

    static SectorDTO getSectorDTOValid() {
        return new SectorDTO(1L, 1L, 10.0, 100.0);
    }

    static InboundOrderDTO getInboundOrderDTOValid() {
        var batchList = Arrays.asList(getBatchDTOValid(), getBatchDTOValid());
        var sector = getSectorDTOValid();
        return new InboundOrderDTO(10, LocalDate.now(), sector, batchList);
    }

    static BatchDTO getBatchDTOValid() {
        return new BatchDTO(1L, 1L, 10f, 10f, 25, 25, LocalDate.now(), LocalDateTime.now(), LocalDate.now());
    }

    static WarehouseResponseDTO getWarehouseResponseDTOValid() {
        var sectors = getListSectorsValid();
        var representative = getRepresentativeValid();
        var warehouse = new Warehouse("Casa central da Argentina", sectors, representative);
        warehouse.setId(1L);
        return WarehouseMapper.toResponseDTO(warehouse);
    }

    static List<Batch> getBatchListValid() {
        var batchsDTO = Arrays.asList(getBatchDTOValid(), getBatchDTOValid());
        return batchsDTO.stream().map(b -> BatchMapper.toModel(b, 1L)).collect(Collectors.toList());
    }
}
