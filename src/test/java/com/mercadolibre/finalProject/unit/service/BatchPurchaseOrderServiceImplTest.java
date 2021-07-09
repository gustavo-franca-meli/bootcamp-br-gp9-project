package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.exceptions.NotFoundException;
import com.mercadolibre.finalProject.repository.BatchPurchaseOrderRepository;
import com.mercadolibre.finalProject.service.impl.BatchPurchaseOrderServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BatchPurchaseOrderServiceImplTest {

    private BatchPurchaseOrderServiceImpl service;
    private final BatchPurchaseOrderRepository repository = mock(BatchPurchaseOrderRepository.class);

    @BeforeEach
    public void setup() {
        service = new BatchPurchaseOrderServiceImpl(repository);
    }

    @Test
    public void shouldFindById(){
        var expected = TestUtils.getBatchPurchaseOrder();
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));

        var got = service.findById(Mockito.anyLong());
        assertEquals(expected.getId(), got.getId());
    }

    @Test
    public void shouldFailFindById(){
        when(repository.findById(Mockito.anyLong())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> service.findById(Mockito.anyLong()));
    }

}
