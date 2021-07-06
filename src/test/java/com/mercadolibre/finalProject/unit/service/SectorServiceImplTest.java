package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.exceptions.NoSpaceInSectorException;
import com.mercadolibre.finalProject.exceptions.SectorNotFoundException;
import com.mercadolibre.finalProject.repository.SectorRepository;
import com.mercadolibre.finalProject.service.impl.BatchServiceImpl;
import com.mercadolibre.finalProject.service.impl.SectorServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class SectorServiceImplTest {

    SectorRepository repository = Mockito.mock(SectorRepository.class);
    SectorServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new SectorServiceImpl(repository);
    }

    @Test
    void shouldFindSectorById() throws SectorNotFoundException {
        var expected = TestUtils.getSectorValid();
        when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));

        var got = service.findById(Mockito.anyLong());
        assertEquals(expected.getId(), got.getId());
    }

    @Test
    void shouldFailFindSectorById() {
        when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(SectorNotFoundException.class, () -> this.service.findById(Mockito.anyLong()));
    }

    @Test
    void shouldCheckSectorExists() {
        var sector = TestUtils.getSectorValid();
        when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.of(sector));

        var got = service.exist(Mockito.anyLong());
        assertTrue(got);
    }

    @Test
    void shouldCheckSectorDoesNotExist() {
        when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        var got = service.exist(Mockito.anyLong());
        assertFalse(got);
    }


    @Test
    void shouldVerifyThereIsSpaceInSector() throws Exception {
        var sector = TestUtils.getSectorValid();
        var batch = TestUtils.getBatchValid();
        when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.of(sector));

        var got = service.isThereSpace(batch, Mockito.anyLong());
        assertTrue(got);
    }

    @Test
    void shouldVerifyThereIsNoSpaceInSector() throws Exception {
        var sector = TestUtils.getSectorValid();
        sector.setMaxQuantityBatches(1000);

        var batch = TestUtils.getBatchValid();
        batch.setCurrentQuantity(1000);

        when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.of(sector));

        assertThrows(NoSpaceInSectorException.class, () -> this.service.isThereSpace(batch, Mockito.anyLong()));
    }
}
