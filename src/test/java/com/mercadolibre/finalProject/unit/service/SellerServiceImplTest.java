package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.model.Seller;
import com.mercadolibre.finalProject.repository.SellerRepository;
import com.mercadolibre.finalProject.service.impl.SellerServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class SellerServiceImplTest {

    SellerRepository repository = Mockito.mock(SellerRepository.class);
    SellerServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new SellerServiceImpl(repository);
    }

    @Test
    public void shouldCreateSeller() {
        var expected = TestUtils.getSeller();
        when(repository.save(Mockito.any())).thenReturn(expected);

        var got = service.createSeller(Mockito.any());
        assertNotNull(got);
    }

    @Test
    public void shouldFindSellerById() {
        var expected = TestUtils.getSeller();
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));

        var got = service.findSellerById(Mockito.anyLong());
        assertEquals(expected.getId(), got.getId());
    }

    @Test
    public void shouldListAllSellers() {
        var expected = new ArrayList<Seller>();
        when(repository.findAll()).thenReturn(expected);

        var got = service.listAllSellers();
        assertEquals(expected.size(), got.size());
    }

    @Test
    public void shouldUpdateSeller() {
        var expected = TestUtils.getSeller();
        when(repository.save(Mockito.any())).thenReturn(expected);

        var got = service.updateSeller(expected);
        assertEquals(expected.getId(), got.getId());
    }
}
