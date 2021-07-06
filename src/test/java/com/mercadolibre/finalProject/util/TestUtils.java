package com.mercadolibre.finalProject.util;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.SectorDTO;
import com.mercadolibre.finalProject.dtos.request.SectorBatchRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.dtos.response.RepresentativeResponseDTO;
import com.mercadolibre.finalProject.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.enums.ProductType;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.model.mapper.RepresentativeMapper;

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

    static Country getCountry() {
        return new Country("Brasil");
    }

    static Warehouse getWarehouseValid() {
        var representative = getRepresentativeValid();
        var country = getCountry();
        return new Warehouse(1L, "São Paulo", country, null, representative);
    }

    static List<Sector> getListSectorsValid() {
        return Arrays.asList(getSectorValid());
    }

    static Sector getSectorValid() {
        var warehouse = getWarehouseValid();
        var batches = getBatchListValid();
        return new Sector(1L, 1, batches, warehouse, 100);
    }

    static Set<Integer> convertSectorsToSetInteger() {
        return Arrays.stream(ProductType.values()).map(ProductType::getCod).collect(Collectors.toSet());
    }

    static Representative getRepresentativeValid() {
        var warehouse = new Warehouse(1L);
        var representative = new Representative("Leonardo", warehouse, null);
        representative.setId(1l);
        return representative;
    }

    static RepresentativeResponseDTO getRepresentativeResponseDTOValid() {
        return RepresentativeMapper.toResponseDTO(getRepresentativeValid());
    }

    static Seller getSellerValid() {
        var seller = new Seller(1L, "Leonardo");
        return seller;
    }

    static Product getProductValid() {
        var seller = getSellerValid();
        return new Product(1L, "Product Name", "Product description", 1.0, 1, seller);
    }

    static InboundOrder getOrderValid() {
        var representative = getRepresentativeValid();
        var batchs = Arrays.asList(getBatchValid());
        return new InboundOrder(LocalDate.now(), representative, batchs);
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
//        var sectors = getListSectorsValid();
//        var representative = getRepresentativeValid();
//        var warehouse = new Warehouse("Casa central da Argentina", sectors, representative);
//        warehouse.setId(1L);
//        return WarehouseMapper.toResponseDTO(warehouse);
        return null;
    }

    static List<Batch> getBatchListValid() {
        return Arrays.asList(getBatchValid(), getBatchValid());
    }

    static Batch getBatchValid() {
        var product = getProductValid();
        var warehouse = new Warehouse(1L);
        var sector = new Sector(1L, 1, null, warehouse, 100);
        return new Batch(1L, product, sector, 0.0f, 0.0f, 1000, 100, LocalDate.now(), LocalDateTime.now(), LocalDate.now().plusWeeks(10));
    }

    static ProductResponseDTO getProductResponseDTO() {
        return new ProductResponseDTO(1L, "Product Name", "Product Description", 10.0, 1);
    }

    static SectorBatchRequestDTO getSectorBatchRequestDTO() {
        return new SectorBatchRequestDTO(1L, 1L, null);
    }

    static SectorBatchRequestDTO getSectorBatchRequestDTOWithOrderedC() {
        return new SectorBatchRequestDTO(1L, 1L, "C");
    }

    static SectorBatchRequestDTO getSectorBatchRequestDTOWithOrderedF() {
        return new SectorBatchRequestDTO(1L, 1L, "F");
    }
}
