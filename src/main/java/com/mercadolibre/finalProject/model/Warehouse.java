package com.mercadolibre.finalProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "warehouses")
@Data
@NoArgsConstructor
public class Warehouse {

    @Id
    private UUID id;

    private String name;

    @OneToMany
    private List<Sector> sectors;

    @OneToOne
    private Representative representative;

    public Warehouse(String name, List<Sector> sectors, Representative representative) {

        this.name = name;
        this.sectors = sectors;
        this.representative = representative;
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

    public List<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative) {
        this.representative = representative;
    }
}
