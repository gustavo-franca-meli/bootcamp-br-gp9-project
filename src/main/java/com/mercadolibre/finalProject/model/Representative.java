package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "representatives")
@Data
@NoArgsConstructor
public class Representative {

    @Id
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    public Representative(String name, Warehouse warehouse) {
        this.name = name;
        this.warehouse = warehouse;
    }

    public Boolean worksIn(String warehouseId) {
        return this.warehouse.getId().toString().equals(warehouseId);
    }
}
