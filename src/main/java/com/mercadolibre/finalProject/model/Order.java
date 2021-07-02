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
    private Long id;

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
}
