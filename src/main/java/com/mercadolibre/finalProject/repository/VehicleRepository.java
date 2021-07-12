package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Vehicle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "SELECT v.* FROM vehicle v WHERE v.plate = :plate", nativeQuery = true)
    Optional<Vehicle> getVehicleByPlate(String plate);

    @Query(value = "SELECT v.* FROM vehicle v WHERE v.warehouse_id = :warehouseId", nativeQuery = true)
    List<Vehicle> getVehiclesByWarehouseId(Long warehouseId);
}
