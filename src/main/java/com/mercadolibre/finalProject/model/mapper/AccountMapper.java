package com.mercadolibre.finalProject.model.mapper;

import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import com.mercadolibre.finalProject.model.Account;

public interface AccountMapper {

    static AccountResponseDTO toResponseDTO (Account account) {
        return new AccountResponseDTO(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                account.getRol(),
                CountryMapper.toResponseDTO(account.getCountry()));
    }
}
