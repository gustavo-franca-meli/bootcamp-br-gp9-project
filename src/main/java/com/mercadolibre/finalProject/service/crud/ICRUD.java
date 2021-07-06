package com.mercadolibre.finalProject.service.crud;

import java.util.List;


public interface ICRUD <requestDTO, responseDTO>{
    responseDTO create(requestDTO dto);

    responseDTO update(Long id, requestDTO dto);

    void delete(Long id);

    responseDTO findById(Long id);

    List<responseDTO> findAll();
}
