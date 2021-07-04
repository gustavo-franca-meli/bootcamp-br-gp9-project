package com.mercadolibre.finalProject.model;

import com.mercadolibre.finalProject.model.enums.SectorType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "products")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    private Long id;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SECTOR_TYPES")
    private Set<Integer> types = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    List<Sector> sectors = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

//    public SectorType getType() {
//        return SectorType.FRAGILE;
//    }

    private void setTypes(Set<SectorType> types) {
        this.types = types.stream().map(SectorType::getCod).collect(Collectors.toSet());
    }

    public Product (Long id) {
        this.id = id;
    }

    public Product (String name, Set<Integer> types, Seller seller) {
        this.name = name;
        this.types = types;
        this.seller = seller;
    }
}
