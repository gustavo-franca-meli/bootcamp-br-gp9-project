package com.mercadolibre.finalProject.model;

import com.mercadolibre.finalProject.model.enums.ProductType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

    @Id
    private Long id;

    private String name;

    private String description;

    private Double price;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SECTOR_PRODUCT_TYPES")
    private Set<Integer> types = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    public Set<ProductType> getProductTypes () {
        return this.types.stream().map(ProductType::toEnum).collect(Collectors.toSet());
    }

    public void setTypes(Set<Integer> types) {
        this.types = types;
    }

    public Product (Long id) {
        this.id = id;
    }

    public Product(String name, String description, Double price, Set<Integer> types, Seller seller) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.types = types;
        this.seller = seller;
    }

    public Product(Long id, String name, Set<Integer> types, Seller seller) {
        this.id = id;
        this.name = name;
        this.types = types;
        this.seller = seller;
    }

}
