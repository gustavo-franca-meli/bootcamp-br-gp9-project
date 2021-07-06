package com.mercadolibre.finalProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "warehouse")
@Data
@NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @OneToMany(mappedBy = "warehouse")
    private List<Sector> sectors = new ArrayList<>();

    @OneToOne
    private Representative representative;

    public Warehouse(Long id) {
        this.id = id;
    }

    public Warehouse(String name, Country country, List<Sector> sectors, Representative representative) {
        this.name = name;
        this.country = country;
        this.sectors = sectors;
        this.representative = representative;
    }

    public Warehouse(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public Warehouse(String name, Representative representative) {
        this.name = name;
        this.representative = representative;
    }
}
