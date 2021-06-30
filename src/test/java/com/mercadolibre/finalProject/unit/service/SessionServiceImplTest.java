package com.mercadolibre.finalProject.unit.service;

import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import com.mercadolibre.finalProject.exceptions.ApiException;
import com.mercadolibre.finalProject.model.Account;
import com.mercadolibre.finalProject.repository.AccountRepository;
import com.mercadolibre.finalProject.service.impl.SessionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SessionServiceImplTest {

    AccountRepository repository = Mockito.mock(AccountRepository.class);
    SessionServiceImpl service;

    @BeforeEach
    void setUp(){
        this.service = new SessionServiceImpl(repository);
    }


    @Test
    void loginFail() {
        when(repository.findByUsernameAndPassword("user", "invalid")).thenReturn(null);
        assertThrows(ApiException.class, () -> service.login("user", "invalid"),
                "Usuario y/o contraseña incorrecto");
    }

    @Test
    void loginOk(){
        Account account = new Account(null, "User", "Pass", null, null);
        when(repository.findByUsernameAndPassword("User", "Pass")).thenReturn(account);
        AccountResponseDTO accountDTO = service.login("User","Pass");
        assertEquals("User", accountDTO.getUsername());
        assertTrue(accountDTO.getToken().startsWith("Bearer"));
    }
}
