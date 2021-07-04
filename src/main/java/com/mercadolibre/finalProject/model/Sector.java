package com.mercadolibre.finalProject.model;

import com.mercadolibre.finalProject.model.enums.SectorType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PRODUCT_SECTOR",
            joinColumns = @JoinColumn(name = "sector_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    List<Product> products = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Batch> batches = new ArrayList<>();

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

    private void addBatch (Batch batch) {
        this.getBatches().add(batch);
    }

    private void addBatches (List<Batch> batches) {
        this.getBatches().addAll(batches);
    }
}
