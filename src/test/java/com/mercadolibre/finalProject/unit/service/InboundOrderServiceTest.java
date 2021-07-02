package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.service.IRepresentativeService;
import com.mercadolibre.finalProject.service.IWarehouseService;
import com.mercadolibre.finalProject.service.impl.InboundOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class InboundOrderServiceTest {

    @Test public void test(){
        Mockito.mock(IWarehouseService.class);
        Mockito.mock(IRepresentativeService.class);
        Mockito.mock(.class);
        Mockito.mock(IRepresentativeService.class);

        new InboundOrderService()

    }
}

