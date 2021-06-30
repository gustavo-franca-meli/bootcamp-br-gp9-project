package com.mercadolibre.finalProject.service.crud;

import java.util.List;

public interface ICRUD <DTO>{
    DTO create(DTO dto);

    DTO update(DTO dto);

    void delete(Long id);

    DTO findById(Long id);

    List<DTO> findAll();
}
