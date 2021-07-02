package com.mercadolibre.finalProject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="country_houses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Unique
    private String country;

    @OneToMany(mappedBy = "countryHouse")
    private List<Account> accounts;

    public CountryHouse(String name, @Unique String country) {
        this.name = name;
        this.country = country;
        this.accounts = new ArrayList<>();
    }

    public CountryHouse(String name, @Unique String country, List<Account> accounts) {
        this.name = name;
        this.country = country;
        this.accounts = accounts;
    }
}
