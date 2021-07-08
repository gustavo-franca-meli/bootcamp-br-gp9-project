package com.mercadolibre.finalProject.dtos.request;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    private String name;
    private String description;
    private Double price;
    private List<Integer> types;
}
