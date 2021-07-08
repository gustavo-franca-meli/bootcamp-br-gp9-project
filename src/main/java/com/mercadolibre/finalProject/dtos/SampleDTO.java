package com.mercadolibre.finalProject.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SampleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int random;

    public SampleDTO(int random) {
        this.random = random;
    }

}
