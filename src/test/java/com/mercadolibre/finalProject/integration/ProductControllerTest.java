package com.mercadolibre.finalProject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ProductControllerTest extends ControllerTest {

    private static final String BASIC_PATH = "/api/v1";
    private static final String PATH = BASIC_PATH + "/fresh-products/list";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
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
    void shouldGetSectorBatchesByProductId() throws Exception {
        var expected = TestUtils.getSectorBatchResponseDTO();
        var expectedBatch = expected.getBatchStock().get(0);
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .header("X-Representative-Id", "1")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "1")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.sector.sector_code").value(expected.getSector().getSectorCode()))
                .andExpect(jsonPath("$.sector.warehouse_code").value(expected.getSector().getWarehouseCode()))
                .andExpect(jsonPath("$.product_id").value(expected.getProductId()))
                .andExpect(jsonPath("$.batch_stock").exists())
                .andExpect(jsonPath("$.batch_stock").isArray())
                .andExpect(jsonPath("$.batch_stock[0].batch_number").value(expectedBatch.getBatchNumber()))
                .andExpect(jsonPath("$.batch_stock[0].current_quantity").value(expectedBatch.getCurrentQuantity()))
                .andExpect(jsonPath("$.batch_stock[0].due_date").value(expectedBatch.getDueDate().toString()));
    }

    @Test
    void shouldGetSectorBatchesByProductIdOrderedByCurrentQuantity() throws Exception {
        var expected = TestUtils.getSectorBatchResponseDTO();
        var expectedBatch = expected.getBatchStock().get(0);
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .header("X-Representative-Id", "1")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "1")
                .param("ordered", "C")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.sector.sector_code").value(expected.getSector().getSectorCode()))
                .andExpect(jsonPath("$.sector.warehouse_code").value(expected.getSector().getWarehouseCode()))
                .andExpect(jsonPath("$.product_id").value(expected.getProductId()))
                .andExpect(jsonPath("$.batch_stock").exists())
                .andExpect(jsonPath("$.batch_stock").isArray())
                .andExpect(jsonPath("$.batch_stock[0].batch_number").value(expectedBatch.getBatchNumber()))
                .andExpect(jsonPath("$.batch_stock[0].current_quantity").value(expectedBatch.getCurrentQuantity()))
                .andExpect(jsonPath("$.batch_stock[0].due_date").value(expectedBatch.getDueDate().toString()));
    }

    @Test
    void shouldGetSectorBatchesByProductIdOrderedByDueDate() throws Exception {
        var expected = TestUtils.getSectorBatchResponseDTO();
        var expectedBatch = expected.getBatchStock().get(1);
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .header("X-Representative-Id", "1")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "1")
                .param("ordered", "F")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.sector.sector_code").value(expected.getSector().getSectorCode()))
                .andExpect(jsonPath("$.sector.warehouse_code").value(expected.getSector().getWarehouseCode()))
                .andExpect(jsonPath("$.product_id").value(expected.getProductId()))
                .andExpect(jsonPath("$.batch_stock").exists())
                .andExpect(jsonPath("$.batch_stock").isArray())
                .andExpect(jsonPath("$.batch_stock[0].batch_number").value(expectedBatch.getBatchNumber()))
                .andExpect(jsonPath("$.batch_stock[0].current_quantity").value(expectedBatch.getCurrentQuantity()))
                .andExpect(jsonPath("$.batch_stock[0].due_date").value(expectedBatch.getDueDate().toString()));
    }

    @Test
    void shouldFailGetSectorBatchesByProductIdOnProductNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "99999")
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFailGetSectorBatchesByProductIdOnBatchNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "2")
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
