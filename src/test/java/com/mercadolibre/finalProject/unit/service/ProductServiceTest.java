package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.dtos.request.ProductRequestDTO;
import com.mercadolibre.finalProject.dtos.response.ProductResponseDTO;
import com.mercadolibre.finalProject.model.Product;
import com.mercadolibre.finalProject.model.Seller;
import com.mercadolibre.finalProject.repository.ProductRepository;
import com.mercadolibre.finalProject.repository.SellerRepository;
import com.mercadolibre.finalProject.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ProductServiceImpl service;

//    @BeforeEach
//    void setUp(){
//        this.service = new ProductServiceImpl(productRepository);
//    }
//
//    @Test
//    void shouldCreateFullProductCorrectly() {
//        var sellerExpected = new Seller(1L, "Carolina Fugita");
//        var responseExpected = new ProductResponseDTO(1L, "Produto1", sellerExpected);
//
//        when(sellerRepository.findById(1L)).thenReturn(java.util.Optional.of(sellerExpected));
//
//        var product1 = new Product( "Produto1", sellerExpected);
//        when(productRepository.save(product1)).thenReturn(new Product(1L, "Produto1", sellerExpected));
//
//        var request = new ProductRequestDTO("Produto1", 1L);
//        var response = service.create(request);
//
//
//    }

    @Test
    void shouldNotCreateProductWithoutSeller() {

    }

    @Test
    void shouldNotCreateProductWithNullParameters() {

    }

    @Test
    void shouldNotCreateProductWithEmptyParameters() {

    }
}
