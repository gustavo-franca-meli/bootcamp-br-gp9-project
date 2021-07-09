package com.mercadolibre.finalProject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.finalProject.model.enums.RoleType;
import com.mercadolibre.finalProject.repository.RepresentativeRepository;
import com.mercadolibre.finalProject.util.CreateFakeLogin;
import com.mercadolibre.finalProject.util.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class PurchaseOrderControllerTest  extends ControllerTest{
    private static final String BASIC_PATH = "/api/v1";
    private static final String PATH = BASIC_PATH + "/fresh-products/purchase-order/";

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
    @SneakyThrows
    @Test
    public void shouldCreateAPurchaseOrderCorrectly(){
        var request = TestUtils.getPurchaseOrderRequestDTO();

        var json = mapper.writeValueAsString(request);

        var expectedObject = TestUtils.getPurchaseOrderResponseDTO();
        expectedObject.setTotalPrice(13.89);

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(expectedObject.getId()))
                .andExpect(jsonPath("$.date").value(expectedObject.getDate().toString()))
                .andExpect(jsonPath("$.total_price").value(expectedObject.getTotalPrice()))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products[0].id").value(expectedObject.getProducts().get(0).getId()))
                .andExpect(jsonPath("$.products[0].product_id").value(expectedObject.getProducts().get(0).getProductId()))
                .andExpect(jsonPath("$.products[0].product_name").value(expectedObject.getProducts().get(0).getProductName()))
                .andExpect(jsonPath("$.products[0].quantity").value(expectedObject.getProducts().get(0).getQuantity()))
                .andExpect(jsonPath("$.products[0].price").value(expectedObject.getProducts().get(0).getPrice()))
                .andExpect(jsonPath("$.products[0].batches").isArray())
                .andExpect(jsonPath("$.products[0].batches[0].id").value(expectedObject.getProducts().get(0).getBatches().get(0).getId()))
                .andExpect(jsonPath("$.products[0].batches[0].batch_id").value(expectedObject.getProducts().get(0).getBatches().get(0).getBatchId()))
                .andExpect(jsonPath("$.products[0].batches[0].quantity").value(expectedObject.getProducts().get(0).getBatches().get(0).getQuantity()))
                .andExpect(jsonPath("$.products[0].batches[0].manufacturing_date").exists())
                .andExpect(jsonPath("$.products[0].batches[0].manufacturing_time").exists())
                .andExpect(jsonPath("$.products[0].batches[0].due_date").exists());
    }
}
