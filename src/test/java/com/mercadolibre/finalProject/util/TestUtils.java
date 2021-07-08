package com.mercadolibre.finalProject.util;

import com.mercadolibre.finalProject.dtos.BatchDTO;
import com.mercadolibre.finalProject.dtos.InboundOrderDTO;
import com.mercadolibre.finalProject.dtos.SectorDTO;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.*;
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
import com.mercadolibre.finalProject.dtos.request.SectorBatchRequestDTO;
import com.mercadolibre.finalProject.dtos.response.*;
import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.enums.ProductType;
import com.mercadolibre.finalProject.model.mapper.RepresentativeMapper;
import com.mercadolibre.finalProject.model.mapper.SectorMapper;

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

    static Country getCountryValid() {
        return new Country(1L);
    }
    static Country getCountry() {
        return new Country("Brasil");
    }

    static Warehouse getWarehouseValid() {
        var country = getCountry();
        var sectors = Arrays.asList(getSectorValid());

        return new Warehouse(1L, "São Paulo", country, sectors, null);
    }

    static List<Sector> getListSectorsValid() {
        return Arrays.asList(getSectorValid());
    }

    static Sector getSectorValid() {
        var warehouse = new Warehouse(1L);
        var batches = getBatchListValid();
        return new Sector(1L, 1, batches, warehouse, 1100);
    }

    static Set<Integer> convertSectorsToSetInteger() {
        return Arrays.stream(ProductType.values()).map(ProductType::getCod).collect(Collectors.toSet());
    }

    static Representative getRepresentativeValid() {
        var representative = new Representative("Leonardo", new Warehouse(1L),getAccountValid());
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

    static InboundOrder getOrderValid() {
        var representative = getRepresentativeValid();
        return new InboundOrder(1L, representative.getId(),LocalDate.now());
    }



    static InboundOrderCreateRequestDTO getInboundOrderDTOValidForCreate() {
        var batchList = Arrays.asList(getBatchDTOValidNoId(), getBatchDTOValidNoId()).stream().map(BatchRequestCreateDTO::new).collect(Collectors.toList());
        var sector = getSectorDTOValid();
        return new InboundOrderCreateRequestDTO(LocalDate.now(), new SectorRequestDTO(sector.getCode(), sector.getWarehouseCode()), batchList);
    }



    static InboundOrderDTO getInboundOrderDTOValidForUpdate() {
        var batchList = Arrays.asList(getBatchDTOValid(), getBatchDTOValid(),getBatchDTOValid(),getBatchDTOValid(),getBatchDTOValid());
        Long i = 1L;
        for(var batch : batchList){
            batch.setId(i);
            i++;
        }
        var sector = getSectorDTOValid();
        return new InboundOrderDTO(1L, LocalDate.now(),sector, batchList);
    }
    static InboundOrderUpdateRequestDTO getInboundOrderUpdateDTOValidForUpdate() {
        var batchList = Arrays.asList( getBatchRequestUpdateDTOValidNoId(),getBatchRequestUpdateDTOValidNoId());
        Long i = 1L;
        for(var batch : batchList){
            batch.setId(i);
            i++;
        }
        var sector = getSectorDTOValid();
        return new InboundOrderUpdateRequestDTO(1L, LocalDate.now(),new SectorRequestDTO(sector.getCode(),sector.getWarehouseCode()), batchList);
    }
    static BatchDTO getBatchDTOValid() {
        return new BatchDTO(1L, 1L, 10f, 10f, 25, 25, LocalDate.now(), LocalDateTime.now(), LocalDate.now());
    }
    static BatchDTO getBatchDTOValidNoId() {
        return new BatchDTO(1L, 10f, 10f, 25, 25, LocalDate.now(), LocalDateTime.now(), LocalDate.now());
    }
    static BatchRequestUpdateDTO getBatchRequestUpdateDTOValidNoId() {
        return new BatchRequestUpdateDTO(1L, 1L,10f, 10f, 25, 25, LocalDate.now(), LocalDateTime.now(), LocalDate.now());
    }



    static SectorResponseDTO getSectorDTOResponseValid() {
        return SectorMapper.toResponseDTO(getSectorValid());
    }


    



    static SectorDTO getSectorDTOValid() {
        return new SectorDTO(1L, 1L, 10.0, 1100.0);
    }

    static InboundOrderDTO getInboundOrderDTOValid() {
        var batchList = Arrays.asList(getBatchDTOValid(), getBatchDTOValid());
        var sector = getSectorDTOValid();
        return new InboundOrderDTO(10L, LocalDate.now(), sector, batchList);
    }



    static WarehouseResponseDTO getWarehouseResponseDTOValid() {
        var sectors = Arrays.asList(getSectorDTOResponseValid());
        return new WarehouseResponseDTO(1L, "São Paulo", sectors);
    }

    static List<Batch> getBatchListValid() {
        return Arrays.asList(getBatchValid(), getBatchValidTwo());
    }

    static Batch getBatchValid() {
        var product = getProductValid();
        var warehouse = new Warehouse(1L);
        var sector = new Sector(1L, 1, null, warehouse, 100);
        var inboundOrder = getOrderValid();
        return new Batch(1L, product, sector, inboundOrder, 0.0f, 0.0f, 1000, 100, LocalDate.now(), LocalDateTime.now(), LocalDate.now().plusWeeks(10));
    }

    static Batch getBatchValidTwo() {
        var product = getProductValid();
        var warehouse = new Warehouse(1L);
        var sector = new Sector(1L, 1, null, warehouse, 100);
        var inboundOrder = getOrderValid();
        return new Batch(1L, product, sector, inboundOrder, 0.0f, 0.0f, 1000, 50, LocalDate.now(), LocalDateTime.now(), LocalDate.now().plusWeeks(5));
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

    static SectorBatchResponseDTO getSectorBatchResponseDTO() {
        var batchIdentificationResponseDTO = getBatchIdentificationResponseDTO();
        var listBatchStockResponseDTO = Arrays.asList(getBatchStockResponseDTO(), getBatchStockResponseDTOTwo());
        return new SectorBatchResponseDTO(batchIdentificationResponseDTO, 2l, listBatchStockResponseDTO);
    }

    static BatchIdentificationResponseDTO getBatchIdentificationResponseDTO() {
        return new BatchIdentificationResponseDTO(4l, 1l);
    }

    static BatchStockResponseDTO getBatchStockResponseDTO() {
        return new BatchStockResponseDTO(7l, 10, LocalDate.of(2021, 12, 12));
    }

    static BatchStockResponseDTO getBatchStockResponseDTOTwo() {
        return new BatchStockResponseDTO(9l, 40, LocalDate.of(2021, 11, 11));
    }
}
