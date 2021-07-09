package com.mercadolibre.finalProject.unit.service;

import com.google.common.collect.Lists;
import com.mercadolibre.finalProject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.finalProject.model.Account;
import com.mercadolibre.finalProject.model.PurchaseOrder;
import com.mercadolibre.finalProject.repository.*;
import com.mercadolibre.finalProject.service.*;
import com.mercadolibre.finalProject.service.impl.*;
import com.mercadolibre.finalProject.util.TestUtils;
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
    PurchaseOrderServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new PurchaseOrderServiceImpl(purchaseOrderRepository, productRepository, batchRepository, batchService, productService, accountRepository);
    }

//    @Test
//    void shouldCreatePurchaseOrder() {
//        PurchaseOrderRequestDTO purchaseOrderRequest = TestUtils.getPurchaseOrderRequestDTO();
//        String username = "onias-rocha";
//        var responseExpected = TestUtils.getPurchaseOrderResponseDTO();
//
//        Optional<Account> accountOpt = Optional.of(TestUtils.getAccountMocked());
//        when(accountRepository.findByUsername(username)).thenReturn(accountOpt);
//
//        when(productService.findById(1L)).thenReturn(TestUtils.getProductResponseDTO());
//
//        Optional<PurchaseOrder> purchaseOrderOpt = Optional.of(TestUtils.getPurchaseOrder());
//        purchaseOrderOpt.get().setProducts(Lists.newArrayList(TestUtils.getProductBatchesPurchaseOrder()));
//        when(purchaseOrderRepository.findById(1L)).thenReturn(purchaseOrderOpt);
//
//        var response = service.getById(id, username);
//
//        verify(purchaseOrderRepository, times(1)).findById(anyLong());
//        verify(accountRepository, times(1)).findByUsername(any());
//
//        assertEquals(responseExpected.getId(), response.getId());
//        assertEquals(responseExpected.getTotalPrice(), response.getTotalPrice());
//        assertEquals(responseExpected.getProducts().size(), response.getProducts().size());
//        assertEquals(responseExpected.getDate(), response.getDate());
//    }

    @Test
    void shouldUpdatePurchaseOrder() {

    }

    @Test
    void shouldGetByIdPurchaseOrder() {
        Long id = 1L;
        String username = "onias-rocha";
        var responseExpected = TestUtils.getPurchaseOrderResponseDTO();

        Optional<PurchaseOrder> purchaseOrderOpt = Optional.of(TestUtils.getPurchaseOrder());
        purchaseOrderOpt.get().setProducts(Lists.newArrayList(TestUtils.getProductBatchesPurchaseOrder()));
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
        purchaseOrder.setProducts(Lists.newArrayList(TestUtils.getProductBatchesPurchaseOrder()));
        when(purchaseOrderRepository.findByBuyerId(1L)).thenReturn(Lists.newArrayList(purchaseOrder));

        var response = service.getAll(username);

        verify(accountRepository, times(1)).findByUsername(any());
        verify(purchaseOrderRepository, times(1)).findByBuyerId(anyLong());

        assertEquals(responseExpected.get(0).getId(), response.get(0).getId());
        assertEquals(responseExpected.get(0).getTotalPrice(), response.get(0).getTotalPrice());
        assertEquals(responseExpected.get(0).getProducts().size(), response.get(0).getProducts().size());
        assertEquals(responseExpected.get(0).getDate(), response.get(0).getDate());
    }
}
