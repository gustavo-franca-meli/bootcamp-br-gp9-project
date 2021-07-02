package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "warehouses")
@Data
@NoArgsConstructor
public class Warehouse {

    @Id
    private Long id;

    private String name;

    @OneToMany
    private List<Sector> sectors;

    @OneToOne
    private Representative representative;

    public Warehouse(String name, List<Sector> sectors, Representative representative) {
        this.name = name;
        this.sectors = sectors;
        this.representative = representative;
    }
}
