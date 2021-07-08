package com.mercadolibre.finalProject.unit.model.enums;

import com.mercadolibre.finalProject.model.enums.RoleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTypeTest {

    @Test
    public void shoudlGetRoleTypeEnumWithCode() {
        var expected = RoleType.REPRESENTATIVE;
        var got = RoleType.toEnum(expected.getCode());

        assertEquals(expected, got);
    }

}
