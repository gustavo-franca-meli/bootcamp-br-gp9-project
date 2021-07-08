package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;

public interface IAccountService {
    AccountResponseDTO findById (Long id);
    AccountResponseDTO findByUsername (String username);
}
