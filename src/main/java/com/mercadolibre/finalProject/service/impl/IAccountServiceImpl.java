package com.mercadolibre.finalProject.service.impl;

import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import com.mercadolibre.finalProject.model.Account;
import com.mercadolibre.finalProject.model.mapper.AccountMapper;
import com.mercadolibre.finalProject.repository.AccountRepository;
import com.mercadolibre.finalProject.service.IAccountService;

import java.util.Optional;

public class IAccountServiceImpl implements IAccountService {
    AccountRepository repository;

    public IAccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    private Account getModelByUsername (String username) {
        return this.repository.findByUsername(username).get(); // should be able to find always
    }

    @Override
    public AccountResponseDTO getAccountByUsername (String username) {
        return AccountMapper.toResponseDTO(this.getModelByUsername(username));
    }
}
