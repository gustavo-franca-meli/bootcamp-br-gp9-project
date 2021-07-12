package com.mercadolibre.finalProject.unit.service;

import com.google.common.collect.Lists;
import com.mercadolibre.finalProject.dtos.ProductStockDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderUpdateRequestDTO;
import com.mercadolibre.finalProject.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.finalProject.model.Account;
import com.mercadolibre.finalProject.model.PurchaseOrder;
import com.mercadolibre.finalProject.model.enums.OrderStatus;
import com.mercadolibre.finalProject.repository.AccountRepository;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.repository.PurchaseOrderRepository;
import com.mercadolibre.finalProject.service.IBatchService;
import com.mercadolibre.finalProject.service.IProductService;
import com.mercadolibre.finalProject.service.IPurchaseOrderService;
import com.mercadolibre.finalProject.service.IRepresentativeService;
import com.mercadolibre.finalProject.service.impl.PurchaseOrderServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PurchaseOrderServiceImplTest {

    PurchaseOrderRepository purchaseOrderRepository = Mockito.mock(PurchaseOrderRepository.class);
    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    BatchRepository batchRepository = Mockito.mock(BatchRepository.class);
    IProductService productService = Mockito.mock(IProductService.class);
    IBatchService batchService = Mockito.mock(IBatchService.class);
    IRepresentativeService representativeService = Mockito.mock(IRepresentativeService.class);
    PurchaseOrderServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new PurchaseOrderServiceImpl(purchaseOrderRepository, productRepository, batchRepository, batchService, productService, accountRepository, representativeService);
    }

    @SneakyThrows
    @Test
    void shouldCreatePurchaseOrder() {
        PurchaseOrderRequestDTO purchaseOrderRequest = TestUtils.getPurchaseOrderRequestDTO();
        String username = "onias-rocha";
        var responseExpected = TestUtils.getPurchaseOrderResponseDTO();

        Optional<Account> accountOpt = Optional.of(TestUtils.getAccountMocked());
        when(accountRepository.findByUsername(any())).thenReturn(accountOpt);

        when(productService.findById(anyLong())).thenReturn(TestUtils.getProductResponseDTO());

        var quantityExpected = 10;
        when(productService.getQuantityOfProductByCountryAndDate(anyLong(), anyLong(), any())).thenReturn(quantityExpected);

        var productValidOpt = Optional.of(TestUtils.getProductMocked());
        when(productRepository.findById(any())).thenReturn(productValidOpt);

        ProductStockDTO productStockDTO = TestUtils.getProductStockDTO();
        when(productService.getStockForProductInCountryByDate(anyLong(), anyLong(), any())).thenReturn(productStockDTO);

        when(purchaseOrderRepository.save(any())).thenReturn(TestUtils.getPurchaseOrder());

        PurchaseOrderResponseDTO response = null;

        response = service.create(purchaseOrderRequest, username);


        verify(accountRepository, times(1)).findByUsername(any());
        verify(productService, times(1)).findById(anyLong());
        verify(productService, times(1)).getQuantityOfProductByCountryAndDate(anyLong(), anyLong(), any());
        verify(purchaseOrderRepository, times(1)).save(any());

        assertEquals(responseExpected.getTotalPrice(), response.getTotalPrice());
        assertEquals(responseExpected.getProducts().size(), response.getProducts().size());
        assertEquals(responseExpected.getDate(), response.getDate());
    }

    @Test
    void shouldUpdatePurchaseOrder() throws Exception {
        PurchaseOrderUpdateRequestDTO purchaseOrderRequest = TestUtils.getPurchaseOrderUpdateRequestDTO();
        var responseExpected = TestUtils.getPurchaseOrderResponseDTO();

        Optional<PurchaseOrder> purchaseOrderOpt = Optional.of(TestUtils.getPurchaseOrder());
        purchaseOrderOpt.get().setProducts(Lists.newArrayList(TestUtils.getProductBatchesPurchaseOrder()));
        when(purchaseOrderRepository.findById(1L)).thenReturn(purchaseOrderOpt);

        when(productService.findById(anyLong())).thenReturn(TestUtils.getProductResponseDTO());

        var quantityExpected = 10;
        when(productService.getQuantityOfProductByCountryAndDate(anyLong(), anyLong(), any())).thenReturn(quantityExpected);

        var productValidOpt = Optional.of(TestUtils.getProductMocked());
        when(productRepository.findById(any())).thenReturn(productValidOpt);

        ProductStockDTO productStockDTO = TestUtils.getProductStockDTO();
        when(productService.getStockForProductInCountryByDate(anyLong(), anyLong(), any())).thenReturn(productStockDTO);

        when(purchaseOrderRepository.save(any())).thenReturn(TestUtils.getPurchaseOrder());

        PurchaseOrderResponseDTO response = null;
        try {
            response = service.update(purchaseOrderRequest);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        verify(purchaseOrderRepository, times(1)).findById(any());
        verify(purchaseOrderRepository, times(1)).save(any());

        assertEquals(responseExpected.getTotalPrice(), response.getTotalPrice());
        assertEquals(responseExpected.getProducts().size(), response.getProducts().size());
        assertEquals(responseExpected.getDate(), response.getDate());
    }

    @Test
    void shouldGetByIdPurchaseOrder() {
        Long id = 1L;
        String username = "onias-rocha";
        var responseExpected = TestUtils.getPurchaseOrderResponseDTO();

        Optional<PurchaseOrder> purchaseOrderOpt = Optional.of(TestUtils.getPurchaseOrder());
        when(purchaseOrderRepository.findById(1L)).thenReturn(purchaseOrderOpt);

        Optional<Account> accountOpt = Optional.of(TestUtils.getAccountMocked());
        when(accountRepository.findByUsername(username)).thenReturn(accountOpt);

        var response = service.getById(id, username);

        verify(purchaseOrderRepository, times(1)).findById(anyLong());
        verify(accountRepository, times(1)).findByUsername(any());

        assertEquals(responseExpected.getId(), response.getId());
        assertEquals(responseExpected.getTotalPrice(), response.getTotalPrice());
        assertEquals(responseExpected.getProducts().size(), response.getProducts().size());
        assertEquals(responseExpected.getDate(), response.getDate());
    }

    @Test
    void shouldGetAllPurchaseOrder() {
        String username = "onias-rocha";
        var responseExpected = Lists.newArrayList(TestUtils.getPurchaseOrderResponseDTO());

        Optional<Account> accountOpt = Optional.of(TestUtils.getAccountMocked());
        when(accountRepository.findByUsername(username)).thenReturn(accountOpt);

        PurchaseOrder purchaseOrder = TestUtils.getPurchaseOrder();
        when(purchaseOrderRepository.findByBuyerId(anyLong())).thenReturn(Lists.newArrayList(purchaseOrder));

        var response = service.getAll(username);

        verify(accountRepository, times(1)).findByUsername(any());
        verify(purchaseOrderRepository, times(1)).findByBuyerId(anyLong());

        assertEquals(responseExpected.get(0).getId(), response.get(0).getId());
        assertEquals(responseExpected.get(0).getTotalPrice(), response.get(0).getTotalPrice());
        assertEquals(responseExpected.get(0).getProducts().size(), response.get(0).getProducts().size());
        assertEquals(responseExpected.get(0).getDate(), response.get(0).getDate());
    }

    @Test
    void shouldUpdateStatus() {
        var username = "onias-rocha";

        var purchaseOrder = TestUtils.getPurchaseOrder();
        when(purchaseOrderRepository.findById(anyLong())).thenReturn(Optional.of(purchaseOrder));

        var representative = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findByAccountUsernameAndWarehouseId(anyString(), anyLong())).thenReturn(representative);

        when(purchaseOrderRepository.save(any())).thenReturn(purchaseOrder);

        var updateStatusRequest = TestUtils.getUpdatePurchaseOrderStatusRequestDTO();
        updateStatusRequest.setRepresentativeUsername(username);
        service.updateStatus(updateStatusRequest);

        verify(batchRepository, times(0)).saveAll(anyList());
    }

    @Test
    void shouldUpdateStatusAndUpdateBatches() {
        String username = "onias-rocha";

        var purchaseOrder = TestUtils.getPurchaseOrder();
        when(purchaseOrderRepository.findById(anyLong())).thenReturn(Optional.of(purchaseOrder));

        var representative = TestUtils.getRepresentativeResponseDTOValid();
        when(representativeService.findByAccountUsernameAndWarehouseId(anyString(), anyLong())).thenReturn(representative);
        when(purchaseOrderRepository.save(any())).thenReturn(purchaseOrder);

        var batch = TestUtils.getBatchValid();
        when(batchRepository.findById(anyLong())).thenReturn(Optional.of(batch));

        var updateStatusRequest = TestUtils.getUpdatePurchaseOrderStatusRequestDTO();
        updateStatusRequest.setRepresentativeUsername(username);
        updateStatusRequest.setStatusOrderCode(6);

        service.updateStatus(updateStatusRequest);

        verify(batchRepository, times(1)).saveAll(anyList());
    }
}
