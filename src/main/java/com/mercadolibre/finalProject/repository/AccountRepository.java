package com.mercadolibre.finalProject.repository;

import com.mercadolibre.finalProject.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("FROM Account a where a.username=:username and a.password=:password ")
    Account findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    Account findByUsernameAndCountryId(String username, Long countryHouseId);

    Optional<Account> findByUsername(String username);
}