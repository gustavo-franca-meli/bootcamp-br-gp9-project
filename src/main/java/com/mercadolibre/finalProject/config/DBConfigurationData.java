package com.mercadolibre.finalProject.config;

import com.mercadolibre.finalProject.config.service.DBService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfigurationData {

    DBService service;

    public DBConfigurationData(DBService service) {
        this.service = service;
    }

    @Bean
    public boolean instantiateDatabase() {
        service.instanceDatabase();
        return true;
    }

}
