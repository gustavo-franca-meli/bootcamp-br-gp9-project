package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.exceptions.NoSpaceInSectorException;
import com.mercadolibre.finalProject.exceptions.SectorNotFound;
import com.mercadolibre.finalProject.model.Batch;
import com.mercadolibre.finalProject.model.Sector;
import com.mercadolibre.finalProject.repository.SectorRepository;
import com.mercadolibre.finalProject.service.impl.SectorServiceImpl;
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
    void setUp(){
        this.service = new SectorServiceImpl(repository);
    }

    @Test
    void shouldFindSectorById () {
        Sector sector = new Sector(1L);
        
        when(this.repository.findById(1L)).thenReturn(Optional.of(sector));

        assertEquals(service.findById(1L),sector);
    }

    @Test
    void shouldNotFindSectorById () {
        when(this.repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(SectorNotFound.class, () -> this.service.findById(2L));
    }

    @Test
    void shouldCheckSectorExists () {
        Sector sector = new Sector(1L);

        when(this.repository.findById(1L)).thenReturn(Optional.of(sector));

        assertTrue(service.exist(1L));
    }

    @Test
    void shouldCheckSectorDoesNotExist () {
        when(this.repository.findById(2L)).thenReturn(Optional.empty());

        assertFalse(service.exist(2L));
    }


    @Test
    void shouldVerifyThereIsSpaceInSector () throws Exception {
        Sector sector = new Sector(1L);
        sector.setCurrentQuantityBatches(100.0);
        sector.setMaxQuantityBatches(1000.0);

        Batch batch = new Batch(2L);
        batch.setCurrentQuantity(100);

        when(this.repository.findById(1L)).thenReturn(Optional.of(sector));

        assertTrue(service.isThereSpace(batch,1L));
    }

    @Test
    void shouldVerifyThereIsNoSpaceInSector () throws Exception {
        Sector sector = new Sector(1L);
        sector.setCurrentQuantityBatches(100.0);
        sector.setMaxQuantityBatches(1000.0);

        Batch batch = new Batch(2L);
        batch.setCurrentQuantity(1000);

        when(this.repository.findById(1L)).thenReturn(Optional.of(sector));

        assertThrows(NoSpaceInSectorException.class, () -> this.service.isThereSpace(batch,1L));
    }
}
