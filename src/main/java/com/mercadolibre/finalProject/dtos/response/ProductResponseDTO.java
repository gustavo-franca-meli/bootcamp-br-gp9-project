package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.model.Seller;
import com.mercadolibre.finalProject.model.enums.ProductType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProductResponseDTO implements Comparable<ProductResponseDTO> {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Set<Integer> types = new HashSet<>();
    private Seller seller;

    public ProductResponseDTO(Long id, String name, String description, Double price, Set<Integer> types) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.types = types;
    }

    public ProductResponseDTO(Long id, String name, String description, Double price, Set<Integer> types, Seller seller) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.types = types;
        this.seller = seller;
    }

    public Set<ProductType> getProductTypes () {
        return this.types.stream().map(ProductType::toEnum).collect(Collectors.toSet());
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public int compareTo(ProductResponseDTO o) {
        return this.name.compareTo(o.getName());
    }
}
