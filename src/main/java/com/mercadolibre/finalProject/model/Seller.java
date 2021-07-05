package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sellers")
@Data
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    private List<Product> products;

    public Seller(Long id, String name) {
        this.id = id;
        this.name = name;
        this.products = new ArrayList<>();
    }

    public Seller(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }
}
