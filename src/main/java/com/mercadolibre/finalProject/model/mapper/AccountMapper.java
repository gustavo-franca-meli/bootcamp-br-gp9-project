package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import com.mercadolibre.finalProject.model.Account;

public interface AccountMapper {

    static AccountResponseDTO toResponseDTO (Account account) {
        return new AccountResponseDTO(
                account.getUsername(),
                account.getPassword(),
                CountryMapper.toResponseDTO(account.getCountry()));
    }
}
