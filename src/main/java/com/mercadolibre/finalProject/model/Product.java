package com.mercadolibre.finalProject.model;

import com.mercadolibre.finalProject.model.enums.SectorType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {

    @Id
    private UUID id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    public SectorType getType() {
        return SectorType.FRAGILE;
    }

    public Product(UUID id) {
        this.id = id;
    }

    public Product(String name, Seller seller) {

        this.name = name;
        this.seller = seller;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
