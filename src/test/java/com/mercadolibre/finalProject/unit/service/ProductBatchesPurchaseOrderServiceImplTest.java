package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.repository.ProductBatchesPurchaseOrderRepository;
import com.mercadolibre.finalProject.service.impl.ProductBatchesPurchaseOrderServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductBatchesPurchaseOrderServiceImplTest {

    private ProductBatchesPurchaseOrderServiceImpl service;
    private final ProductBatchesPurchaseOrderRepository repository = mock(ProductBatchesPurchaseOrderRepository.class);

    @BeforeEach
    public void setup() {
        service = new ProductBatchesPurchaseOrderServiceImpl(repository);
    }

    @Test
    public void shouldFindById() {
        var expected = TestUtils.getProductBatchesPurchaseOrder();
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));

        var got = service.findById(Mockito.anyLong());
        assertEquals(expected.getId(), got.getId());
    }

    @Test
    public void shouldFailFindById() {
        when(repository.findById(Mockito.anyLong())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> service.findById(Mockito.anyLong()));
    }

}
