package com.mercadolibre.finalProject.config;

import com.mercadolibre.finalProject.security.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static String BASIC_PATH = "/api/v1";
    private static String FRESH_PRODUCTS_ROUTE = BASIC_PATH + "/fresh-products";

    private static String[] REPRESENTATIVE = {FRESH_PRODUCTS_ROUTE + "/inboundorder", FRESH_PRODUCTS_ROUTE + "/list", FRESH_PRODUCTS_ROUTE + "/warehouse", "/seller/**"};


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/sign-in").permitAll()
                .antMatchers(HttpMethod.GET, "/ping").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
                .antMatchers(HttpMethod.GET, "/fake").permitAll()
                .antMatchers(HttpMethod.POST, FRESH_PRODUCTS_ROUTE + "/return-order").hasRole("BUYER")
                .antMatchers(HttpMethod.PUT, FRESH_PRODUCTS_ROUTE + "/return-order").hasRole("REPRESENTATIVE")
                .antMatchers(REPRESENTATIVE).hasRole("REPRESENTATIVE")

                .anyRequest().authenticated();
    }
}
