package com.mercadolibre.finalProject.dtos.response;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class CountryResponseDTO implements Comparable<CountryResponseDTO>{

    private Long id;

    private String name;

    public CountryResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(@NotNull CountryResponseDTO o) {
        return this.name.compareTo(o.getName());
    }
}
