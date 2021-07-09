package com.mercadolibre.finalProject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class BatchControllerTest extends ControllerTest {

    private static final String BASIC_PATH = "/api/v1";
    private static final String PATH = BASIC_PATH + "/fresh-products/due-date";
    private String token = "";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    public void setUp() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(BASIC_PATH + "/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "onias-rocha")
                .param("password", "pass123"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        var account = mapper.readValue(result, AccountResponseDTO.class);
        token = account.getToken();
    }

    @Test
    void shouldGetBatchesBySector() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("sectorId", "1")
                .param("daysQuantity", "100")
        )
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetBatchesByProductType() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/list")
                .header("X-Representative-Id", "1")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("daysQuantity", "100")
                .param("category", "FS")
                .param("direction", "asc")
        )
                .andExpect(status().isOk());
    }
}
