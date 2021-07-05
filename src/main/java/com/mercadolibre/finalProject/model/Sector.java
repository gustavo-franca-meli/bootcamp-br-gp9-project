package com.mercadolibre.finalProject.model;

import com.mercadolibre.finalProject.model.enums.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "sector")
@Data
@NoArgsConstructor
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SECTOR_TYPES")
    private Set<Integer> types = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Batch> batches = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    private Integer maxQuantityBatches;

    public Sector(Long id) {
        this.id = id;
    }

    public Sector(Set<Integer> types, Warehouse warehouse, Integer maxQuantityBatches) {
        this.types = types;
        this.warehouse = warehouse;
        this.maxQuantityBatches = maxQuantityBatches;
    }

    private void setTypes (Set<ProductType> types) {
        this.types = types.stream().map(ProductType::getCod).collect(Collectors.toSet());
    }

    public Set<ProductType> getTypesInProductType () {
        return this.types.stream().map(ProductType::toEnum).collect(Collectors.toSet());
    }
}
