package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.model.Account;
import com.mercadolibre.finalProject.model.Country;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Seller;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.repository.WarehouseRepository;
import com.mercadolibre.finalProject.service.ISellerService;
import com.mercadolibre.finalProject.service.impl.ProductServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ISellerService sellerService = Mockito.mock(ISellerService.class);
    WarehouseRepository warehouseRepository = Mockito.mock(WarehouseRepository.class);
    ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new ProductServiceImpl(productRepository, sellerService, warehouseRepository);
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
        verify(productRepository, times(1)).save(any());
        assertEquals(responseExpected, response);
    }

    @NotNull
    private ProductRequestDTO createProductRequestDTO() {
        return new ProductRequestDTO("Produto1", "", 10.0, 1);
    }

    @NotNull
    private Product createExpectedProduct(Seller sellerExpected) {
        return new Product(
                1L,
                "Produto1",
                "",
                10.0,
                1,
                sellerExpected);
    }

    @NotNull
    private ProductResponseDTO createExpectedProductResponseDTO(Seller sellerExpected) {
        return new ProductResponseDTO(
                1L,
                "Produto1",
                "",
                10.0,
                1);
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
