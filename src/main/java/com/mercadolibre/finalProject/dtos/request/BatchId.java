package com.mercadolibre.finalProject.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BatchId {
    Long id;

    public BatchId(Long id) {
        this.id = id;
    }
}
