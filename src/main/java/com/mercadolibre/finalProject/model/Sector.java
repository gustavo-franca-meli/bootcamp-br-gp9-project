package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<Batch> batches;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    private Integer maxQuantityBatches;

    public Sector(Long id) {
        this.id = id;
    }

    public Sector(Long id, Integer sectorType, List<Batch> batches, Warehouse warehouse, Integer maxQuantityBatches) {
        this.id = id;
        this.sectorType = sectorType;
        this.batches = batches;
        this.warehouse = warehouse;
        this.maxQuantityBatches = maxQuantityBatches;
    }

    public Sector(Integer sectorType, Warehouse warehouse, Integer maxQuantityBatches) {
        this.sectorType = sectorType;
        this.warehouse = warehouse;
        this.maxQuantityBatches = maxQuantityBatches;
    }

    public Long getWareHouseId() {
        return this.warehouse.getId();
    }

    public Long getWarehouseId() {
        return this.warehouse.getId();
    }
}
