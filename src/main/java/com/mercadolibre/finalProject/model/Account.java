package com.mercadolibre.finalProject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "account", indexes = {@Index(name = "username_index", columnList = "username")})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private Integer rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country_fk", nullable = false)
    private Country country;

    public Account(String username, String password, Integer rol, Country country) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.country = country;
    }
}