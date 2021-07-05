package com.mercadolibre.finalProject.unit.model.mapper;

import com.mercadolibre.finalProject.model.enums.SectorType;
import com.mercadolibre.finalProject.model.mapper.RepresentativeMapper;
import com.mercadolibre.finalProject.model.mapper.SectorMapper;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SectorMapperTest {

    @Test
    void shouldAssembleListSectorResponseDTO() {
        var expected = TestUtils.getListSectorsValid();
        var got = SectorMapper.toListResponseDTO(expected);

        assertEquals(expected.get(0).getId(), got.get(0).getId());
    }

    @Test
    void shouldAssembleSectorResponseDTO() {
        var expected = TestUtils.getSectorValid();
        var got = SectorMapper.toResponseDTO(expected);

        assertEquals(expected.getId(), got.getId());
    }

    @Test
    void shouldAssembleSetTypesSectorDescription() {
        var expected = TestUtils.getSectorValid();
        var got = SectorMapper.getTypesSectorDescriptions(expected.getTypes());

        assertTrue(got.contains(SectorType.PERISHABLE.getDescription()));
    }
}
