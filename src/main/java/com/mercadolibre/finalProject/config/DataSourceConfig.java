package com.mercadolibre.finalProject.config;

import com.fury.api.FuryUtils;
import com.fury.api.exceptions.FuryDecryptException;
import com.fury.api.exceptions.FuryNotFoundAPPException;
import com.fury.api.exceptions.FuryUpdateException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"com.mercadolibre.finalProject.repository"})
public class DataSourceConfig {

    @Bean
    @Qualifier("datasource")
    @Profile({"!local & !integration_test"})
    public DataSource getDataSource(
            final @Value("${spring.datasource.host}") String host,
            final @Value("${spring.datasource.db}") String db,
            final @Value("${spring.datasource.username}") String user,
            final @Value("${spring.datasource.password}") String password
    )
            throws FuryDecryptException, FuryNotFoundAPPException, FuryUpdateException {
        return DataSourceBuilder.create()
                .url(String.format("jdbc:mysql://%s/%s?serverTimezone=UTC", FuryUtils.getEnv(host), db))
                .username(user)
                .password(FuryUtils.getEnv(password))
                .build();
    }

    @Bean
    @Qualifier("datasource")
    @Profile("local")
    public DataSource getDataSourceTest(
            final @Value("${spring.datasource.host}") String host,
            final @Value("${spring.datasource.db}") String db,
            final @Value("${spring.datasource.username}") String user,
            final @Value("${spring.datasource.password}") String password
    ) {
        return DataSourceBuilder.create()
                .url(String.format("jdbc:mysql://%s/%s?serverTimezone=UTC", host, db))
                .username(user)
                .password(password)
                .build();
    }

    @Bean
    @Qualifier("datasource")
    @Profile("integration_test")
    public DataSource getDataSourceTest() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:testdb")
                .username("sa")
                .build();
    }
}