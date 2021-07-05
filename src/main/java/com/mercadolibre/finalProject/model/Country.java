package com.mercadolibre.finalProject.model;

import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="country")
@Data
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Unique
    private String name;

    @OneToMany(mappedBy = "country")
    private List<Account> accounts;

    @OneToMany(mappedBy = "country")
    private List<Warehouse> warehouses;

    public Country(@Unique String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public Country(@Unique String name, List<Account> accounts) {
        this.name = name;
        this.accounts = accounts;
    }
}
