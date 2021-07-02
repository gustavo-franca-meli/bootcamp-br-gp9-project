package com.mercadolibre.finalProject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String username, password;

    private Integer rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country_house_fk", nullable = false)
    private CountryHouse countryHouse;

    public Account(String username, String password, Integer rol, CountryHouse countryHouse) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.countryHouse = countryHouse;
    }
}