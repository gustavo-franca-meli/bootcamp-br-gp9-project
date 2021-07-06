package com.mercadolibre.finalProject.util;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.SectorDTO;
import com.mercadolibre.finalProject.dtos.response.RepresentativeResponseDTO;
import com.mercadolibre.finalProject.dtos.response.SectorResponseDTO;
import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.enums.ProductType;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.model.mapper.RepresentativeMapper;
import com.mercadolibre.finalProject.model.mapper.SectorMapper;
import com.mercadolibre.finalProject.model.mapper.WarehouseMapper;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

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
        var country = getCountryValid();
        var warehouse = new Warehouse("Casa central da Argentina", country);
        warehouse.setId(1L);
        return warehouse;
    }

    static Country getCountryValid() {
        return new Country(1L);
    }

    static List<Sector> getListSectorsValid() {
        return Arrays.asList(getSectorValid());
    }

    static Sector getSectorValid() {
        var types = convertSectorsToSetInteger().stream().map(ProductType::toEnum).collect(Collectors.toSet());
        var sector = new Sector();
        sector.setId(1l);
        sector.setTypes(types);
        sector.setWarehouse(getWarehouseValid());
        sector.setMaxQuantityBatches(10);
        return sector;
    }

    static Set<Integer> convertSectorsToSetInteger() {
        return Arrays.stream(ProductType.values()).map(ProductType::getCod).collect(Collectors.toSet());
    }

    static Representative getRepresentativeValid() {
        var representative = new Representative("Leonardo", getWarehouseValid(),getAccountValid());
        representative.setId(1l);
        return representative;
    }

    static Account getAccountValid() {
        return new Account();
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
        //return new Product("Pizza", null);
        return new Product(1L);
    }

    static Batch getBatchValid() {
        var product = getProductValid();
        var sector = getSectorValid();
        var order = getOrderValid();
        return new Batch(1L, product, sector,order, 0.0f, 0.0f, 10, 5, LocalDate.now(), LocalDateTime.now(), LocalDate.now());
    }

    static InboundOrder getOrderValid() {
        var representative = getRepresentativeValid();
        return new InboundOrder(LocalDate.now(), representative.getId());
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
        var warehouse = getWarehouseValid();
       return WarehouseMapper.toResponseDTO(warehouse);
    }

    static List<Batch> getBatchListValid() {
        var batchsDTO = Arrays.asList(getBatchDTOValid(), getBatchDTOValid());
        var sector = getSectorValid();
        var order = getOrderValid();
        return batchsDTO.stream().map(b -> BatchMapper.toModel(b, sector.getId(),order.getId() )).collect(Collectors.toList());
    }

    static SectorResponseDTO getSectorDTOResponseValid() {
        return SectorMapper.toResponseDTO(getSectorValid());
    }
}
