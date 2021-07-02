package com.mercadolibre.finalProject.model;

import com.mercadolibre.finalProject.model.enums.SectorType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "sectors")
@Data
@NoArgsConstructor
public class Sector {

    @Id
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SECTOR_TYPES")
    private Set<Integer> types = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    private Double currentQuantityBatches;
    private Double maxQuantityBatches;

    public Sector(Long id) {
        this.id = id;
    }

    public Sector(Set<Integer> types, Warehouse warehouse, Double currentQuantityBatches, Double maxQuantityBatches) {
        this.types = types;
        this.warehouse = warehouse;
        this.currentQuantityBatches = currentQuantityBatches;
        this.maxQuantityBatches = maxQuantityBatches;
    }

    private void getTypes(Set<SectorType> types) {
        this.types = types.stream().map(SectorType::getCod).collect(Collectors.toSet());
    }
}
