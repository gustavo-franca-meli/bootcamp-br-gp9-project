package com.mercadolibre.finalProject.dtos.response;

import com.mercadolibre.finalProject.model.Seller;

public class ProductResponseDTO implements Comparable<ProductResponseDTO> {

    private Long id;

    private String name;

    private Seller seller;

    public ProductResponseDTO(Long id, String name, Seller seller) {
        this.id = id;
        this.name = name;
        this.seller = seller;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public int compareTo(ProductResponseDTO o) {
        return this.name.compareTo(o.getName());
    }
}
