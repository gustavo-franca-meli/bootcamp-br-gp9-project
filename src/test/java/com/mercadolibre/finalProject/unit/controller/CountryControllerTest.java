package com.mercadolibre.finalProject.unit.controller;

import com.mercadolibre.finalProject.controller.CountryController;
import com.mercadolibre.finalProject.model.enums.RoleType;
import com.mercadolibre.finalProject.service.ICountryService;
import com.mercadolibre.finalProject.util.CreateFakeLogin;
import com.mercadolibre.finalProject.util.TestUtils;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CountryControllerTest {

    CountryController controller;
    ICountryService service = Mockito.mock(ICountryService.class);
    private String token;

    @BeforeEach
    void setUp() throws NotFoundException {
        controller = new CountryController(service);
        token = CreateFakeLogin.loginValid("onias-rocha", RoleType.REPRESENTATIVE);
    }

    @Test
    void shouldCreateCountry() throws Exception {
        var requestDTO = TestUtils.getCountryRequestDTO();

        var response = TestUtils.getCountryResponseDTO();
        when(service.create(requestDTO)).thenReturn(response);

        var got = controller.create(Mockito.anyString(), requestDTO);

        assertEquals(requestDTO.getName(), got.getBody().getName());
    }

}
