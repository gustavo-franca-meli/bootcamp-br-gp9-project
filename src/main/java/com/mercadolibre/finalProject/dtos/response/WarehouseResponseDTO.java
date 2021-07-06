package com.mercadolibre.finalProject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class WarehouseResponseDTO {

    private Long id;
    private String name;
    private List<SectorResponseDTO> sectors;
    private RepresentativeResponseDTO representative;
}
