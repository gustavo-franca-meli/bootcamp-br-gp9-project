package com.mercadolibre.finalProject.unit.model.enums;

import com.mercadolibre.finalProject.model.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderStatusTest {

    @Test
    public void shoudlGetOrderStatusWithCode() {
        var expected = OrderStatus.PROCESSING;
        var got = OrderStatus.toEnum(expected.getCod());

        assertEquals(expected, got);
        assertEquals(expected.getDescription(), got.getDescription());
    }

}
