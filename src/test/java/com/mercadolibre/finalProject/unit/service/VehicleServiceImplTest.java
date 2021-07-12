package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.model.Account;
import com.mercadolibre.finalProject.model.PurchaseOrder;
import com.mercadolibre.finalProject.repository.*;
import com.mercadolibre.finalProject.service.*;
import com.mercadolibre.finalProject.service.impl.VehicleServiceImpl;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VehicleServiceImplTest {

    VehicleRepository vehicleRepository = Mockito.mock(VehicleRepository.class);
    IWarehouseService warehouseService = Mockito.mock(IWarehouseService.class);
    VehicleServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new VehicleServiceImpl(vehicleRepository, warehouseService);
    }

    @Test
    void shouldGetVehicleByIdCorrectly() {
        var responseExpected = TestUtils.getVehicleResponseDTO();

        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(TestUtils.getVehicle()));

        var response = service.findById(anyLong());

        verify(vehicleRepository, times(1)).findById(anyLong());

        assertEquals(responseExpected.getId(), response.getId());
        assertEquals(responseExpected.getCapacity(), response.getCapacity());
        assertEquals(responseExpected.getVehicleType(), response.getVehicleType());
        assertEquals(responseExpected.getPlate(), response.getPlate());
        assertEquals(responseExpected.getIsAvailable(), response.getIsAvailable());
        assertEquals(responseExpected.getWarehouse_id(), response.getWarehouse_id());
    }

    @Test
    void shouldGetAllVehiclesCorrectly() {
        var responseExpected = TestUtils.getVehiclesListResponseDTO();

        when(vehicleRepository.findAll()).thenReturn(TestUtils.getVehiclesList());

        var response = service.findAll();

        verify(vehicleRepository, times(1)).findAll();

        assertEquals(responseExpected.get(0).getId(), response.get(0).getId());
        assertEquals(responseExpected.get(0).getCapacity(), response.get(0).getCapacity());
        assertEquals(responseExpected.get(0).getVehicleType(), response.get(0).getVehicleType());
        assertEquals(responseExpected.get(0).getPlate(), response.get(0).getPlate());
        assertEquals(responseExpected.get(0).getIsAvailable(), response.get(0).getIsAvailable());
        assertEquals(responseExpected.get(0).getWarehouse_id(), response.get(0).getWarehouse_id());
    }

    @Test
    void shouldGetVehicleByPlateCorrectly() {
        var responseExpected = TestUtils.getVehicleResponseDTO();

        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(TestUtils.getVehicle()));

        var response = service.findById(anyLong());

        verify(vehicleRepository, times(1)).findById(anyLong());

        assertEquals(responseExpected.getId(), response.getId());
        assertEquals(responseExpected.getCapacity(), response.getCapacity());
        assertEquals(responseExpected.getVehicleType(), response.getVehicleType());
        assertEquals(responseExpected.getPlate(), response.getPlate());
        assertEquals(responseExpected.getIsAvailable(), response.getIsAvailable());
        assertEquals(responseExpected.getWarehouse_id(), response.getWarehouse_id());
    }

    @Test
    void shouldGetVehiclesByWarehouseIdCorrectly() {
        var responseExpected = TestUtils.getVehiclesListResponseDTO();

        when(warehouseService.findWarehouseById(anyLong())).thenReturn(TestUtils.getWarehouseValid());
        when(vehicleRepository.getVehiclesByWarehouseId(anyLong())).thenReturn(TestUtils.getVehiclesList());

        var response = service.findVehiclesByWarehouseId(anyLong());

        verify(warehouseService, times(1)).findWarehouseById(anyLong());
        verify(vehicleRepository, times(1)).getVehiclesByWarehouseId(anyLong());

        assertEquals(responseExpected.get(0).getId(), response.get(0).getId());
        assertEquals(responseExpected.get(0).getCapacity(), response.get(0).getCapacity());
        assertEquals(responseExpected.get(0).getVehicleType(), response.get(0).getVehicleType());
        assertEquals(responseExpected.get(0).getPlate(), response.get(0).getPlate());
        assertEquals(responseExpected.get(0).getIsAvailable(), response.get(0).getIsAvailable());
        assertEquals(responseExpected.get(0).getWarehouse_id(), response.get(0).getWarehouse_id());
    }
}
