package com.mercadolibre.finalProject.service.crud;

import java.util.List;
import java.util.UUID;

public interface ICRUD <DTO>{
    DTO create(DTO dto);

    DTO update(DTO dto);

    void delete(UUID id);

    DTO findById(UUID id);

    List<DTO> findAll();
}
