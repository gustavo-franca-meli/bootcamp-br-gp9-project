package com.mercadolibre.finalProject.config.service;

import com.mercadolibre.finalProject.model.Account;
import com.mercadolibre.finalProject.model.CountryHouse;
import com.mercadolibre.finalProject.repository.AccountRepository;
import com.mercadolibre.finalProject.repository.CountryHouseRepository;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    CountryHouseRepository countryHouseRepository;
    AccountRepository accountRepository;

    public DBService(CountryHouseRepository countryHouseRepository, AccountRepository accountRepository) {
        this.countryHouseRepository = countryHouseRepository;
        this.accountRepository = accountRepository;
    }

    public void instanceDatabase() {
        var country1 = new CountryHouse("Argentina", "Casa central de Argentina");
        var country2 = new CountryHouse("Chile", "Casa central de Chile");
        var country3 = new CountryHouse("Uruguay", "Casa central de Uruguay");
        var country4 = new CountryHouse("Colombia", "Casa central de Colombia");
        countryHouseRepository.saveAll(Lists.newArrayList(country1, country2, country3, country4));

        var account1 = new Account("user_one", "contra123", 1, country1);
        var account2 = new Account("user_two", "contra123", 2, country2);
        var account3 = new Account("user_three", "contra123", 3, country3);
        var account4 = new Account("user_four", "contra123", 4, country4);
        accountRepository.saveAll(Lists.newArrayList(account1, account2, account3, account4));
    }
}
