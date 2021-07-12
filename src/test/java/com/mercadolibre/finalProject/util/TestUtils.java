package com.mercadolibre.finalProject.util;

import com.google.common.collect.Lists;
import com.mercadolibre.finalProject.dtos.*;
import com.mercadolibre.finalProject.dtos.request.*;
import com.mercadolibre.finalProject.dtos.request.inboundOrder.*;
import com.mercadolibre.finalProject.dtos.response.*;
import com.mercadolibre.finalProject.model.*;
import com.mercadolibre.finalProject.model.enums.*;
import com.mercadolibre.finalProject.model.mapper.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

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
        var representative = new Representative("Leonardo", new Warehouse(1L), getAccountValid());
        representative.setId(1l);
        return representative;
    }

    static Account getAccountValid() {
        var acc = new Account();
        acc.setId(1L);
        acc.setUsername("A Name Interesting");
        acc.setPassword("A Strong Password");
        acc.setRol(RoleType.REPRESENTATIVE.getCode());
        var country = getCountry();
        country.setId(1L);
        acc.setCountry(country);
        return acc;
    }

    static Account getAccountMocked() {
        var country = new Country("Argentina");
        country.setId(1L);
        return new Account(1L, "onias-rocha", "pass123", 1, country);
    }

    static RepresentativeResponseDTO getRepresentativeResponseDTOValid() {
        return RepresentativeMapper.toResponseDTO(getRepresentativeValid());
    }


    static Product getProductValid() {
        return new Product(1L);
    }

    static Product getProductMocked() {
        return new Product(1L,
                "Produto1",
                "",
                13.89,
                1,
                new Seller());
    }

    static InboundOrder getOrderValid() {
        var representative = getRepresentativeValid();
        return new InboundOrder(1L, representative.getId(), LocalDate.now());
    }


    static InboundOrderCreateRequestDTO getInboundOrderDTOValidForCreate() {
        var batchList = Arrays.asList(getBatchDTOValidNoId(), getBatchDTOValidNoId()).stream().map(BatchRequestCreateDTO::new).collect(Collectors.toList());
        var sector = getSectorDTOValid();
        return new InboundOrderCreateRequestDTO(LocalDate.now(), new SectorRequestDTO(sector.getCode(), sector.getWarehouseCode()), batchList);
    }


    static InboundOrderDTO getInboundOrderDTOValidForUpdate() {
        var batchList = Arrays.asList(getBatchDTOValid(), getBatchDTOValid(), getBatchDTOValid(), getBatchDTOValid(), getBatchDTOValid());
        Long i = 1L;
        for (var batch : batchList) {
            batch.setId(i);
            i++;
        }
        var sector = getSectorDTOValid();
        return new InboundOrderDTO(1L, LocalDate.now(), sector, batchList);
    }

    static InboundOrderUpdateRequestDTO getInboundOrderUpdateDTOValidForUpdate() {
        var batchList = Arrays.asList(getBatchRequestUpdateDTOValidNoId(), getBatchRequestUpdateDTOValidNoId());
        Long i = 1L;
        for (var batch : batchList) {
            batch.setId(i);
            i++;
        }
        var sector = getSectorDTOValid();
        return new InboundOrderUpdateRequestDTO(1L, LocalDate.now(), new SectorRequestDTO(sector.getCode(), sector.getWarehouseCode()), batchList);
    }

    static BatchDTO getBatchDTOValid() {
        return new BatchDTO(1L, 1L, 10f, 10f, 25, 25, LocalDate.now(), LocalDateTime.now(), LocalDate.now());
    }

    static BatchDTO getBatchDTOValidNoId() {
        return new BatchDTO(1L, 10f, 10f, 25, 25, LocalDate.now(), LocalDateTime.now(), LocalDate.now());
    }

    static BatchRequestUpdateDTO getBatchRequestUpdateDTOValidNoId() {
        return new BatchRequestUpdateDTO(1L, 1L, 10f, 10f, 25, 25, LocalDate.now(), LocalDateTime.now(), LocalDate.now());
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
        return new ProductResponseDTO(1L, "Product Name", "Product Description", 10.0, 1, "Perishable");
    }

    static SectorBatchRequestDTO getSectorBatchRequestDTO() {
        return new SectorBatchRequestDTO(1L, "onias-rocha", null);
    }

    static SectorBatchRequestDTO getSectorBatchRequestDTOWithOrderedC() {
        return new SectorBatchRequestDTO(1L, "onias-rocha", "C");
    }

    static SectorBatchRequestDTO getSectorBatchRequestDTOWithOrderedF() {
        return new SectorBatchRequestDTO(1L, "onias-rocha", "F");
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

    static CountryRequestDTO getCountryRequestDTO() {
        return new CountryRequestDTO("Brasil");
    }

    static CountryResponseDTO getCountryResponseDTO() {
        return new CountryResponseDTO(1L, "Brasil");
    }

    static ProductRequestDTO createProductRequestDTO() {
        return new ProductRequestDTO("Produto1", "", 10.0, 1);
    }

    static Product createExpectedProduct(Seller sellerExpected) {
        return new Product(
                1L,
                "Produto1",
                "",
                10.0,
                1,
                sellerExpected);
    }

    static BatchPurchaseOrder getBatchPurchaseOrder() {
        var batch = getBatchValid();
        return new BatchPurchaseOrder(1, batch);
    }

    static BatchDTO getBatchDTO() {
        return new BatchDTO(1L, 1L, 10.0f, 10.0f, 1, 1, LocalDate.now(), LocalDateTime.now(), LocalDate.now().plusWeeks(3));
    }

    static BatchPurchaseOrderDTO getBatchPurchaseOrderDTO() {
        var batchDTO = getBatchDTO();
        return new BatchPurchaseOrderDTO(1L, 10, batchDTO);
    }

    static BatchPurchaseOrderResponseDTO getBatchPurchaseOrderResponseDTO() {
        return new BatchPurchaseOrderResponseDTO(2L, 3L, 1, LocalDate.of(2021,4,10), LocalDateTime.of(2021,04,10,0,0), LocalDate.of(2021,4,10));
    }

    static PurchaseOrderRequestDTO getPurchaseOrderRequestDTO() {
        return new PurchaseOrderRequestDTO(
                LocalDate.of(2021,7,1),
                1L,
                1,
                Lists.newArrayList(new ProductPurchaseOrderRequestDTO(1L, 1))
        );
    }

    static PurchaseOrderUpdateRequestDTO getPurchaseOrderUpdateRequestDTO() {
        return new PurchaseOrderUpdateRequestDTO(1L, 1L, 1);
    }

    static PurchaseOrderUpdateRequestDTO getPurchaseOrderDownsizeRequestDTO() {
        return new PurchaseOrderUpdateRequestDTO(1L, 1L, 1);
    }

    static PurchaseOrderUpdateRequestDTO getPurchaseOrderUpsizeRequestDTO() {
        return new PurchaseOrderUpdateRequestDTO(1L, 1L, 3);
    }

    static PurchaseOrderUpdateRequestDTO getPurchaseOrderAddProductRequestDTO() {
        return new PurchaseOrderUpdateRequestDTO(1L, 2L, 1);
    }

    static PurchaseOrder getPurchaseOrder() {
        var account = getAccountMocked();
        var purchaseProduct = new ProductBatchesPurchaseOrder(getProductMocked(), 13.89, null);
        purchaseProduct.setPurchaseBatches(List.of(getBatchPurchaseOrder()));
        var purchaseOrder =  new PurchaseOrder(2L, account, LocalDate.of(2021,7,1), 1,List.of(purchaseProduct));
        return purchaseOrder;
    }

    static PurchaseOrderResponseDTO getPurchaseOrderResponseDTO() {
        return new PurchaseOrderResponseDTO(
                2L,
                LocalDate.of(2021,7,1),
                13.89,
                Lists.newArrayList(new ProductBatchesPurchaseOrderResponseDTO(
                        2L,
                        1L,
                        "Bisteca Suína",
                        1,
                        13.89,
                        Lists.newArrayList(getBatchPurchaseOrderResponseDTO())))
        );
    }


    static PurchaseOrderResponseDTO getExistingPurchaseOrderResponseDTO() {
        return new PurchaseOrderResponseDTO(
                1L,
                LocalDate.of(2021,7,9),
                27.78,
                Lists.newArrayList(new ProductBatchesPurchaseOrderResponseDTO(
                        1L,
                        1L,
                        "Bisteca Suína",
                        2,
                        27.78,
                        Lists.newArrayList(new BatchPurchaseOrderResponseDTO(
                                1L,
                                3L,
                                2,
                                LocalDate.of(2021,4,10),
                                LocalDateTime.of(LocalDate.of(2021,4,10),LocalTime.of(21,0)),
                                LocalDate.of(2021,7,30)
                        ))))
        );
    }

    static PurchaseOrderResponseDTO getExistingPurchaseOrderUpdatedResponseDTO() {
        return new PurchaseOrderResponseDTO(
                1L,
                LocalDate.of(2021,7,9),
                13.89,
                Lists.newArrayList(new ProductBatchesPurchaseOrderResponseDTO(
                        1L,
                        1L,
                        "Bisteca Suína",
                        1,
                        13.89,
                        Lists.newArrayList(new BatchPurchaseOrderResponseDTO(
                                1L,
                                3L,
                                1,
                                LocalDate.of(2021,4,10),
                                LocalDateTime.of(LocalDate.of(2021,4,10),LocalTime.of(21,0)),
                                LocalDate.of(2021,7,30)
                        ))))
        );
    }

    static PurchaseOrderResponseDTO getExistingPurchaseOrderUpsizedResponseDTO() {
        return new PurchaseOrderResponseDTO(
                1L,
                LocalDate.of(2021,7,9),
                41.67,
                Lists.newArrayList(new ProductBatchesPurchaseOrderResponseDTO(
                        1L,
                        1L,
                        "Bisteca Suína",
                        3,
                        41.67,
                        Lists.newArrayList(new BatchPurchaseOrderResponseDTO(
                                1L,
                                3L,
                                3,
                                LocalDate.of(2021,4,10),
                                LocalDateTime.of(LocalDate.of(2021,4,10),LocalTime.of(21,0)),
                                LocalDate.of(2021,7,30)
                        ))))
        );
    }

    static PurchaseOrderResponseDTO getExistingPurchaseOrderAddProductResponseDTO() {
        return new PurchaseOrderResponseDTO(
                1L,
                LocalDate.of(2021,7,9),
                28.68,
                Lists.newArrayList(new ProductBatchesPurchaseOrderResponseDTO(
                        1L,
                        1L,
                        "Bisteca Suína",
                        2,
                        27.78,
                        Lists.newArrayList(new BatchPurchaseOrderResponseDTO(
                                1L,
                                3L,
                                2,
                                LocalDate.of(2021,4,10),
                                LocalDateTime.of(LocalDate.of(2021,4,10),LocalTime.of(21,0)),
                                LocalDate.of(2021,7,30)
                        ))),
                        new ProductBatchesPurchaseOrderResponseDTO(
                                2L,
                                2L,
                                "Batata Lavada 1 Unidade 300g",
                                1,
                                0.9,
                                Lists.newArrayList(new BatchPurchaseOrderResponseDTO(
                                        2L,
                                        9L,
                                        1,
                                        LocalDate.of(2021,4,10),
                                        LocalDateTime.of(LocalDate.of(2021,4,10),LocalTime.of(21,0)),
                                        LocalDate.of(2021,11,11)
                                ))))
        );
    }

    static ProductBatchesPurchaseOrder getProductBatchesPurchaseOrder() {
        var product = TestUtils.getProductValid();
        var purchaseOrder = TestUtils.getPurchaseOrder();
        return new ProductBatchesPurchaseOrder(product, 13.89, purchaseOrder);
    }

    static ProductResponseDTO createExpectedProductResponseDTO(Seller sellerExpected) {
        return new ProductResponseDTO(
                1L,
                "Produto1",
                "",
                10.0,
                1,
                "Fresh");
    }

    static Seller createExpectedSeller() {
        return new Seller(
                1L,
                "Seller1",
                new Account(
                        "seller1",
                        "123",
                        1,
                        new Country("Brasil")));
    }

    static List<ProductResponseDTO> getListProductResponse() {
        var listOfProduct = getListOfProducts();
        return ProductMapper.toListResponseDTO(listOfProduct);
    }

    static List<Product> getListOfProducts() {
        return Arrays.asList(createExpectedProduct(createExpectedSeller()), createExpectedProduct(createExpectedSeller()), createExpectedProduct(createExpectedSeller()), createExpectedProduct(createExpectedSeller()));
    }

    static WarehouseProductSumDTO getWarehouseProductSumDTO() {
        return new WarehouseProductSumDTO(1L, 1);
    }

    static ProductStockDTO getProductStockDTO() {
        return new ProductStockDTO(1L, "Produto1", 13.89, new ArrayList<>());
    }

    static SumOfProductStockDTO getSumOfProductStockDTO() {
        var listWareHouseProductSumDTO = Arrays.asList(getWarehouseProductSumDTO());
        return new SumOfProductStockDTO(1L, listWareHouseProductSumDTO);
    }

    static Seller getSeller() {
        var account = getAccountValid();
        return new Seller(1L, "seller", account);
    }

    static Vehicle getVehicle() {
        return new Vehicle(1L, 1, 1, "ABC-1234", true, new Warehouse(1L));
    }

    static List<Vehicle> getVehiclesList() {
        return Lists.newArrayList(getVehicle());
    }

    static VehicleResponseDTO getVehicleResponseDTO() {
        return new VehicleResponseDTO(1L, VehicleType.toEnum(1), 1, "ABC-1234", true, 1L);
    }

    static List<VehicleResponseDTO> getVehiclesListResponseDTO() {
        return Lists.newArrayList(getVehicleResponseDTO());
    }
}
