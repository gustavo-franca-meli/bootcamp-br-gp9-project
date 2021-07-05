package com.mercadolibre.finalProject.model;

import com.mercadolibre.finalProject.model.enums.SectorType;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "products")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    public SectorType getType() {
        return SectorType.FRAGILE;
    }

    public Product(Long id) {
        this.id = id;
    }

    public Product(String name, Seller seller) {
        this.name = name;
        this.seller = seller;
    }
}
