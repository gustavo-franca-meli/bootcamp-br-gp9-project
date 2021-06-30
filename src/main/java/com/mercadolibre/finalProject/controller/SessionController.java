package com.mercadolibre.finalProject.controller;

import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import com.mercadolibre.finalProject.service.ISessionService;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/v1")
@RestController
public class SessionController {
    private final ISessionService service;

    public SessionController(ISessionService sessionService) {
        this.service = sessionService;
    }

    /**
     * Realiza la validación del usuario y contraseña ingresado.
     * En caso de ser correcto, devuelve la cuenta con el token necesario para realizar las demás consultas.
     *
     * @param username
     * @param password
     * @return
     * @throws NotFoundException
     */
    @PostMapping("/sign-in")
    public AccountResponseDTO login(@RequestParam("username") String username, @RequestParam("password") String password) throws NotFoundException {
        return service.login(username, password);
    }

}
