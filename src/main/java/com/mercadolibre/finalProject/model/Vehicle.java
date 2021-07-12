package com.mercadolibre.finalProject.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_type")
    private Integer vehicleType;

    private Integer capacity;

    private String plate;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    public Vehicle(Integer vehicleType, Integer capacity, String plate, Warehouse warehouse) {
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.plate = plate;
        this.isAvailable = true;
        this.warehouse = warehouse;
    }
}
