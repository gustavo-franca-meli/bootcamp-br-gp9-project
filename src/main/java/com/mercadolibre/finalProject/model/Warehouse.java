package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "warehouses")
@Data
@NoArgsConstructor
public class Warehouse {

    @Id
    private UUID id;
    private String name;

    @OneToMany
    private List<Sector> sectors;

    @OneToOne
    private Representative representative;
}
