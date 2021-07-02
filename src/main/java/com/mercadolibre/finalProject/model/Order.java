package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private LocalDate orderDate;

    @OneToMany
    private List<Batch> batches;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Representative representative;

    public Order(LocalDate orderDate, Representative representative, List<Batch> bathStock) {

        this.orderDate = orderDate;
        this.batches = bathStock;
        this.representative = representative;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative) {
        this.representative = representative;
    }
}
