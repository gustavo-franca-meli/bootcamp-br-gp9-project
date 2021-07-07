package com.mercadolibre.finalProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "batch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    @ManyToOne
    @JoinColumn(name = "inbound_order_id",nullable = false)
    private InboundOrder inboundOrder;

    private Float currentTemperature;
    private Float minimumTemperature;
    private Integer initialQuantity;
    private Integer currentQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;

    public Batch(Long id,
                 Product product,
                 Sector sector,
                 Float currentTemperature,
                 Float minimumTemperature,
                 Integer initialQuantity,
                 Integer currentQuantity,
                 LocalDate manufacturingDate,
                 LocalDateTime manufacturingTime,
                 LocalDate dueDate) {

        this.id = id;
        this.product = product;
        this.sector = sector;
        this.currentTemperature = currentTemperature;
        this.minimumTemperature = minimumTemperature;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.manufacturingDate = manufacturingDate;
        this.manufacturingTime = manufacturingTime;
        this.dueDate = dueDate;
    }

    public Batch(Product product,
                 Sector sector,
                 InboundOrder inboundOrder,
                 Float currentTemperature,
                 Float minimumTemperature,
                 Integer initialQuantity,
                 Integer currentQuantity,
                 LocalDate manufacturingDate,
                 LocalDateTime manufacturingTime,
                 LocalDate dueDate) {
        this.product = product;
        this.sector = sector;
        this.inboundOrder = inboundOrder;
        this.currentTemperature = currentTemperature;
        this.minimumTemperature = minimumTemperature;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.manufacturingDate = manufacturingDate;
        this.manufacturingTime = manufacturingTime;
        this.dueDate = dueDate;
    }

    public Batch(Long id) {
        this.id = id;
    }

    public void withdrawQuantity(Integer quantityTakenFromBatch) {
        Integer newQuantity = this.getCurrentQuantity() - quantityTakenFromBatch;
        this.setCurrentQuantity(newQuantity);
    }

    public Integer getSectortype() {
        return this.sector.getSectorType();
    }
}