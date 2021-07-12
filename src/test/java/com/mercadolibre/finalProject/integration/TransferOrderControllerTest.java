package com.mercadolibre.finalProject.integration;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TransferOrderControllerTest extends ControllerTest {
    private static final String BASIC_PATH = "/api/v1";
    private static final String PATH = BASIC_PATH + "/fresh-products/transfer-order/";
    private String token;
    private String tokenInvalid;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Mock
    private RepresentativeRepository representativeRepository;

    @BeforeEach
    public void setUp() throws Exception {
        token = CreateFakeLogin.loginValid("onias-rocha", RoleType.REPRESENTATIVE);
        tokenInvalid = CreateFakeLogin.loginInvalid("user", RoleType.REPRESENTATIVE);
    }

    @Test
    void shouldCreateTransferOrderCorrectly() throws Exception {
        var request = TestUtils.getTransferOrderRequestValid();

        var json = mapper.writeValueAsString(request);

        var expectedBatch = TestUtils.getTransferOrderResponseValid();

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.transfer_order_id").value(expectedBatch.getTransferOrderId()))
                .andExpect(jsonPath("$.batch_stock").exists())
                .andExpect(jsonPath("$.batch_stock").isArray())
                .andExpect(jsonPath("$.batch_stock[0].id").value(expectedBatch.getBatchStock().get(1).getId()))
                .andExpect(jsonPath("$.batch_stock[1].id").value(expectedBatch.getBatchStock().get(2).getId()))
                .andExpect(jsonPath("$.batch_stock[2].id").value(expectedBatch.getBatchStock().get(3).getId()));
    }

    @Test
    void shouldReturnErrorWhenBatchNotFound() throws Exception {
        var request = TestUtils.getTransferOrderRequestValid();

        var json = mapper.writeValueAsString(request);

        var expectedBatch = TestUtils.getTransferOrderResponseValid();

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.transfer_order_id").value(expectedBatch.getTransferOrderId()))
                .andExpect(jsonPath("$.batch_stock").exists())
                .andExpect(jsonPath("$.batch_stock").isArray())
                .andExpect(jsonPath("$.batch_stock[0].id").value(expectedBatch.getBatchStock().get(1).getId()))
                .andExpect(jsonPath("$.batch_stock[1].id").value(expectedBatch.getBatchStock().get(2).getId()))
                .andExpect(jsonPath("$.batch_stock[2].id").value(expectedBatch.getBatchStock().get(3).getId()));
    }
    @Test
    void shouldReturnNotFoundWhenRepresentativeNotWorkInWarehouse() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();

        var json = mapper.writeValueAsString(request);
        System.out.println(json);

        var expectedMessage = "The representative doesn't work in this warehouse. Username: user";
        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", tokenInvalid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }
    @Test
    void shouldReturnNotFoundWhenWarehouseToTransferNotExist() throws Exception {
        var request = TestUtils.getTransferOrderRequestValid();


        var invalidWarehouseId = 30L;
        request.setToWarehouseId(invalidWarehouseId);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Warehouse Not Found. Id:" + invalidWarehouseId;

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnAnErrorListOfBatchWhenBatchNotRegistered() throws Exception {
        var request = TestUtils.getTransferOrderRequestValid();


        request.getBatchStock().get(0).setId(100L);
        request.getBatchStock().get(1).setId(101L);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Not found 2 batch";

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").exists())
                .andExpect(jsonPath("$.errors[1].message").exists())
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] transfer batch position 0 error: The batch doesn't exist in warehouse. Id: 100"));
    }

    //não pode retornar o batch quando requisitar a rota de listar batches


}
