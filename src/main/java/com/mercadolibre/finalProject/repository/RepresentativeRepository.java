package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepresentativeRepository extends JpaRepository<Representative, Long> {

    Optional<Representative> findByIdAndWarehouseId(Long representativeId, Long warehouseId);


    @Query(value = "SELECT r.* FROM Representative r " +
            "INNER JOIN Account a ON r.account_id = a.id " +
            "WHERE a.username = :username ", nativeQuery = true)
    public Optional<Representative> findRepresentativeByAccountUsername(String username);

    @Query(value = "SELECT r.* FROM Representative r " +
            "INNER JOIN Account a ON r.account_id = a.id " +
            "WHERE a.username = :username AND r.warehouse_id = :warehouseId", nativeQuery = true)
    public Optional<Representative> findRepresentativeByAccountUsernameAndWarehouseId(String username, Long warehouseId);

}
