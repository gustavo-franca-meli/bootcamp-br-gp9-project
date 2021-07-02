package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


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

    public Representative(String name) {
        this.name = name;
    }

    public Boolean worksIn(Long warehouseId) {
        return this.warehouse.getId().equals(warehouseId);
    }
}
