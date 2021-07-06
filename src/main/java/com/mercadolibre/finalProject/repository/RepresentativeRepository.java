package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepresentativeRepository extends JpaRepository<Representative, Long> {

    Optional<Representative> findByIdAndWarehouseId(Long representativeId, Long warehouseId);

}
