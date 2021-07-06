package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "seller")
@Data
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public Seller(Long id) {
        this.id = id;
    }

    public Seller(Long id, String name) {
        this.id = id;
        this.name = name;
        this.products = new ArrayList<>();
    }

    public Seller(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public Seller(String name, Account account) {
        this.name = name;
        this.account = account;
    }
}
