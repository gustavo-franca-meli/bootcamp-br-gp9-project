package com.mercadolibre.finalProject.unit.model.enums;

import com.mercadolibre.finalProject.model.enums.ProductType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTypeTest {

    @Test
    public void shoudlGetProductTypeEnumWithCode() {
        var expected = ProductType.FRESH;
        var got = ProductType.toEnum(expected.getCod());

        assertEquals(expected, got);
    }

}
