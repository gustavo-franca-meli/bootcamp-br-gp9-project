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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SECTOR_TYPES")
    private Set<Integer> types = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    private Double currentQuantityBatches;
    private Double maxQuantityBatches;

    public Sector(Long id, Set<Integer> types, Warehouse warehouse, Double currentQuantityBatches, Double maxQuantityBatches) {
        this.id = id;
        this.types = types;
        this.warehouse = warehouse;
        this.currentQuantityBatches = currentQuantityBatches;
        this.maxQuantityBatches = maxQuantityBatches;
    }

    private void getTypes(Set<SectorType> types) {
        this.types = types.stream().map(SectorType::getCod).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Integer> getTypes() {
        return types;
    }

    public void setTypes(Set<Integer> types) {
        this.types = types;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Double getCurrentQuantityBatches() {
        return currentQuantityBatches;
    }

    public void setCurrentQuantityBatches(Double currentQuantityBatches) {
        this.currentQuantityBatches = currentQuantityBatches;
    }

    public Double getMaxQuantityBatches() {
        return maxQuantityBatches;
    }

    public void setMaxQuantityBatches(Double maxQuantityBatches) {
        this.maxQuantityBatches = maxQuantityBatches;
    }
}
