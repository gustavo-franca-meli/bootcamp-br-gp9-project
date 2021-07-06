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

    private Integer sectorType;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Batch> batches = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    private Integer maxQuantityBatches;

    public Sector(Long id) {
        this.id = id;
    }

    public Sector(Integer sectorType, Warehouse warehouse, Integer maxQuantityBatches) {
        this.sectorType = sectorType;
        this.warehouse = warehouse;
        this.maxQuantityBatches = maxQuantityBatches;
    }

    public Long getWareHouseId() {
        return this.warehouse.getId();
    }
}
