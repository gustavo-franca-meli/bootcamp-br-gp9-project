package com.mercadolibre.finalProject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import com.mercadolibre.finalProject.model.mapper.BatchMapper;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BatchControllerTest {

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
        var expectedBatch = BatchMapper.toListBatchValidateDateResponseDTO(TestUtils.getBatchListValid());
        var expected = ResponseEntity.ok(expectedBatch);

        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .header("X-Representative-Id", "1")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("sectorId", "1")
                .param("daysQuantity", "30")
        )
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetBatchesByProductType() throws Exception {
        var expectedBatch = BatchMapper.toListBatchValidateDateResponseDTO(TestUtils.getBatchListValid());
        var expected = ResponseEntity.ok(expectedBatch);

        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .header("X-Representative-Id", "1")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("daysQuantity", "30")
                .param("category", "FF")
                .param("direction", "asc")
        )
                .andExpect(status().isOk());
    }
}
