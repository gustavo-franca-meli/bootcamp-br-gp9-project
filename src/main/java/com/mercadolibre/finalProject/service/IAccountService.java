package com.mercadolibre.finalProject.service;

import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;

public interface IAccountService {
    AccountResponseDTO getAccountByUsername (String username);
}
