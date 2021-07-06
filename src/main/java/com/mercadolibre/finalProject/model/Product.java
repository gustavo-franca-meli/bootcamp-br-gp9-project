package com.mercadolibre.finalProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    public Product (Long id) {
        this.id = id;
    }

    public Product(Long id, String name, String description, Double price, Integer productType, Seller seller) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productType = productType;
        this.seller = seller;
    }

    public Product(String name, String description, Double price, Integer productType, Seller seller) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productType = productType;
        this.seller = seller;
    }
}
