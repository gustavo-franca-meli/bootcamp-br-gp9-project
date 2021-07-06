package com.mercadolibre.finalProject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BatchIdentificationResponseDTO {

    private final Long sectorCode;
    private final Long warehouseCode;

}
