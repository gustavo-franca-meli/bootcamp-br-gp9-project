package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.dtos.response.SumOfProductStockDTO;
import com.mercadolibre.finalProject.dtos.response.WarehouseProductSumDTO;
import com.mercadolibre.finalProject.model.Account;
import com.mercadolibre.finalProject.model.Country;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Seller;
import com.mercadolibre.finalProject.model.enums.ProductType;
import com.mercadolibre.finalProject.model.mapper.AccountMapper;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.repository.BatchRepository;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.repository.SellerRepository;
import com.mercadolibre.finalProject.repository.WarehouseRepository;
import com.mercadolibre.finalProject.service.IAccountService;
import com.mercadolibre.finalProject.service.ISellerService;
import com.mercadolibre.finalProject.service.impl.ProductServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ISellerService sellerService = Mockito.mock(ISellerService.class);
    IAccountService accountService = Mockito.mock(IAccountService.class);
    BatchRepository batchRepository = Mockito.mock(BatchRepository.class);
    WarehouseRepository warehouseRepository = Mockito.mock(WarehouseRepository.class);
    ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new ProductServiceImpl(productRepository,accountService ,sellerService,batchRepository, warehouseRepository);
    }

    @Test
    void shouldCreateFullProductCorrectly() {
        var sellerExpected = TestUtils.createExpectedSeller();
        var responseExpected = TestUtils.createExpectedProductResponseDTO(sellerExpected);
        when(sellerService.findSellerById(1L)).thenReturn(sellerExpected);

        var product1 = TestUtils.createExpectedProduct(sellerExpected);
        when(productRepository.save(Mockito.any())).thenReturn(product1);

        var request = TestUtils.createProductRequestDTO();
        var response = service.create(request);

        verify(sellerService, times(1)).findSellerById(any());
        verify(productRepository, times(1)).save(any());
        assertEquals(responseExpected, response);
    }

    @Test
    void shouldUpdateFullProductCorrectly() {
        var sellerExpected = TestUtils.createExpectedSeller();
        var responseExpected = TestUtils.createExpectedProductResponseDTO(sellerExpected);

        var productId = 10L;

        responseExpected.setId(productId);

        when(sellerService.findSellerById(1L)).thenReturn(sellerExpected);

        var product1 = TestUtils.createExpectedProduct(sellerExpected);
        product1.setId(productId);
        when(productRepository.save(Mockito.any())).thenReturn(product1);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product1));

        var request = TestUtils.createProductRequestDTO();

        var response = service.update(productId,request);

        verify(sellerService, times(1)).findSellerById(any());
        verify(productRepository, times(1)).save(any());
        assertEquals(responseExpected, response);
    }

    @Test
    void shouldDeleteProductCorrectly() {
        var productIdToDelete  = 10L;
        assertDoesNotThrow(() ->service.delete(productIdToDelete));
        verify(productRepository,times(1)).deleteById(productIdToDelete);

    }

    @Test
    void shouldReturnAProductList() {
        var responseExpected  = TestUtils.getListProductResponse();

        when(productRepository.findAll()).thenReturn(TestUtils.getListOfProducts());
        var response = service.findAll();
        assertEquals(responseExpected,response);
    }

    @Test
    void shouldReturnATotalPrice() {
        Double expected = 100.50;
        var productId = 10L;
        var quantity = 5;
        var product = TestUtils.createExpectedProduct(TestUtils.createExpectedSeller());
        product.setId(10L);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        var total = service.getTotalPrice(productId,quantity);

        assertEquals(total,product.getPrice() * quantity);

    }

    @Test
    void shouldReturnStockForProductInCountryByDate() {
        var productId = 10L;
        var countryId = 1L;
        var product = TestUtils.createExpectedProduct(TestUtils.createExpectedSeller());
        var date = LocalDate.now();
        var listBatchResponse = TestUtils.getBatchListValid();
        product.setId(10L);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(batchRepository.findByProductCountryAndDate(productId,countryId,date)).thenReturn(listBatchResponse);

        var expected = BatchMapper.toListDTO(listBatchResponse);

        var response = service.getStockForProductInCountryByDate(productId,countryId, LocalDate.now());

        assertEquals(expected,response.getBatches());

    }

    @Test
    void shouldReturnQuantityOfProductByCountryAndDate() {
        var productId = 10L;
        var countryId = 1L;
        var date = LocalDate.now();
        var expected = 10;
        when(batchRepository.getProductQuantityByCountryAndDate(productId,countryId,date)).thenReturn(expected);
        var response = service.getQuantityOfProductByCountryAndDate(productId,countryId,date);
        assertEquals(expected,response);
    }
    @Test
    void shouldReturnZeroWhenNotFoundBatches() {
        var productId = 10L;
        var countryId = 1L;
        var date = LocalDate.now();
        var expected = 0;
        when(batchRepository.getProductQuantityByCountryAndDate(productId,countryId,date)).thenReturn(null);
        var response = service.getQuantityOfProductByCountryAndDate(productId,countryId,date);
        assertEquals(expected,response);
    }

    @Test
    void shouldReturnProductByCountry() {
        var account = AccountMapper.toResponseDTO(TestUtils.getAccountValid());

        var expected = TestUtils.getListProductResponse();

        var products = TestUtils.getListOfProducts();

        when(accountService.getAccountByUsername(account.getUsername())).thenReturn(account);
        when(productRepository.findByCountry(account.getCountry().getId())).thenReturn(products);

        var response = service.getProductsByCountry(account.getUsername(),null);
        assertEquals(expected,response);
    }

    @Test
    void shouldReturnProductByCountryAndProductType() {
        var account = AccountMapper.toResponseDTO(TestUtils.getAccountValid());
        var productType = ProductType.FROZEN.getCod();

        var expected = TestUtils.getListProductResponse();

        var products = TestUtils.getListOfProducts();

        when(accountService.getAccountByUsername(account.getUsername())).thenReturn(account);
        when(productRepository.findByCountryAndType(account.getCountry().getId(),productType)).thenReturn(products);

        var response = service.getProductsByCountry(account.getUsername(),productType);
        assertEquals(expected,response);
    }

    @Test
    void shouldReturnSumOfProductStockInAllWarehouses() {



        var productId = 10L;

        var listWarehouseSum = new ArrayList<WarehouseProductSumDTO>();
        var warehouseProductSumDTO = new WarehouseProductSumDTO(1l,10);
        listWarehouseSum.add(warehouseProductSumDTO);
        listWarehouseSum.add(warehouseProductSumDTO);
        listWarehouseSum.add(warehouseProductSumDTO);
        listWarehouseSum.add(warehouseProductSumDTO);

        var expected = new SumOfProductStockDTO(productId, listWarehouseSum);

        var sum = mock(ProductRepository.ISumOfProductStockDTO.class);

        when(sum.getQuantity()).thenReturn(warehouseProductSumDTO.getTotalQuantity().toString());
        when(sum.getWarehouse_id()).thenReturn(warehouseProductSumDTO.getWarehouseCode().toString());

        var sumList = new ArrayList<ProductRepository.ISumOfProductStockDTO>();
        sumList.add(sum);
        sumList.add(sum);
        sumList.add(sum);
        sumList.add(sum);

        when(productRepository.getSumOfProductStockInAllWarehouses(productId)).thenReturn(sumList);


        var response = service.getSumOfProductStockInAllWarehouses(productId);

        assertEquals(expected,response);
    }






}
