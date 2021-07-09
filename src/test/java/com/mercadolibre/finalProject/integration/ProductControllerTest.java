package com.mercadolibre.finalProject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.finalProject.model.enums.RoleType;
import com.mercadolibre.finalProject.util.CreateFakeLogin;
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

    private static final String BASIC_PATH = "/api/v1/fresh-products";
    private static final String PATH_LIST = BASIC_PATH + "/list";
    private static final String PATH_WAREHOUSE = BASIC_PATH + "/warehouse";
    private String token = "";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    public void setUp() throws Exception {
        token = CreateFakeLogin.loginValid("onias-rocha", RoleType.REPRESENTATIVE);
    }

    @Test
    void shouldGetSectorBatchesByProductId() throws Exception {
        var expected = TestUtils.getSectorBatchResponseDTO();
        var expectedBatch = expected.getBatchStock().get(0);
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH_LIST)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "2")
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
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH_LIST)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "2")
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

        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH_LIST)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "2")
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
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH_LIST)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "99999")
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFailGetSectorBatchesByProductIdOnBatchNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH_LIST)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "3")
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Doesn't has valid batches with this product. Id product: 3"));
    }

    @Test
    void shouldGetProductsByCountry() throws Exception {
        var expected = TestUtils.getProductResponseDTO();
        this.mockMvc.perform(MockMvcRequestBuilders.get(BASIC_PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productType", "1")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(expected.getId()));
    }

    @Test
    void shouldGetSumOfProductInAllWarehouses() throws Exception {
        var expected = TestUtils.getSumOfProductStockDTO();
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH_WAREHOUSE)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "1")
                .param("countryId", "1")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.product_id").value(expected.getProductId()))
                .andExpect(jsonPath("$.warehouses").exists())
                .andExpect(jsonPath("$.warehouses").isArray());
    }
}
