package com.mercadolibre.finalProject.model;

import com.mercadolibre.finalProject.model.enums.SectorType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "sectors")
@Data
@NoArgsConstructor
public class Sector {

    @Id
    private String id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SECTOR_TYPES")
    private Set<Integer> types = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    private Double maxQuantityBatches;

    public Sector(String id, Set<Integer> types, Warehouse warehouse, Double maxQuantityBatches) {
        this.id = id;
        this.types = types;
        this.warehouse = warehouse;
        this.maxQuantityBatches = maxQuantityBatches;
    }

    private void getTypes(Set<SectorType> types) {
        this.types = types.stream().map(SectorType::getCod).collect(Collectors.toSet());
    }

    public Sector(String id) {
        this.id = id;
    }
}
