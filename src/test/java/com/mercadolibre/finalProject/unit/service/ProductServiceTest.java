package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.model.Account;
import com.mercadolibre.finalProject.model.Country;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Seller;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.repository.SellerRepository;
import com.mercadolibre.finalProject.service.ISellerService;
import com.mercadolibre.finalProject.service.impl.ProductServiceImpl;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ISellerService sellerService = Mockito.mock(ISellerService.class);
    ProductServiceImpl service;

    @BeforeEach
    void setUp(){
        this.service = new ProductServiceImpl(productRepository, sellerService);
    }

    @Test
    void shouldCreateFullProductCorrectly() {
        var sellerExpected = createExpectedSeller();
        var responseExpected = createExpectedProductResponseDTO(sellerExpected);

        when(sellerService.findSellerById(1L)).thenReturn(sellerExpected);

        var product1 = createExpectedProduct(sellerExpected);
        when(productRepository.save(Mockito.any())).thenReturn(product1);

        var request = createProductRequestDTO();
        var response = service.create(request);

        verify(sellerService, times(1)).findSellerById(any());
        verify(productRepository, times(1)). save(any());
        assertEquals(responseExpected, response);
    }

    @NotNull
    private ProductRequestDTO createProductRequestDTO() {
        return new ProductRequestDTO("Produto1", "", 10.0, Lists.newArrayList(1, 2, 3), 1L);
    }

    @NotNull
    private Product createExpectedProduct(Seller sellerExpected) {
        return new Product(
                1L,
                "Produto1",
                "",
                10.0,
                new HashSet<>(Arrays.asList(1, 2, 3)),
                sellerExpected);
    }

    @NotNull
    private ProductResponseDTO createExpectedProductResponseDTO(Seller sellerExpected) {
        return new ProductResponseDTO(
                1L,
                "Produto1",
                "",
                10.0,
                new HashSet<>(Arrays.asList(1, 2, 3)),
                sellerExpected);
    }

    @NotNull
    private Seller createExpectedSeller() {
        return new Seller(
                1L,
                "Seller1",
                new Account(
                    "seller1",
                    "123",
                    1,
                    new Country("Brasil")));
    }
}
