package com.mercadolibre.finalProject.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.finalProject.model.enums.RoleType;
import com.mercadolibre.finalProject.repository.RepresentativeRepository;
import com.mercadolibre.finalProject.util.CreateFakeLogin;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ReturnOrderControllerTest extends ControllerTest {
    private static final String BASIC_PATH = "/api/v1";
    private static final String PATH = BASIC_PATH + "/fresh-products/return-order";
    private String tokenBuyer;
    private String tokenRepresentative;
    private String tokenInvalid;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    public void setUp() throws Exception {
        tokenBuyer = CreateFakeLogin.loginValid("onias-rocha", RoleType.BUYER);
        tokenRepresentative = CreateFakeLogin.loginValid("onias-rocha", RoleType.REPRESENTATIVE);
        tokenInvalid = CreateFakeLogin.loginInvalid("user", RoleType.REPRESENTATIVE);
    }

    @Test
    void shouldCreateReturnOrder() throws Exception {
        var request = TestUtils.getReturnOrderRequestDTO();

        var json = mapper.writeValueAsString(request);

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", tokenBuyer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void shouldUpdateStatusReturnOrder() throws Exception {
        var request = TestUtils.getUpdateReturnOrderDTO();

        var json = mapper.writeValueAsString(request);

        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("Authorization", tokenRepresentative)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}

