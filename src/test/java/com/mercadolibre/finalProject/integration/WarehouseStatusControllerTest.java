package com.mercadolibre.finalProject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.finalProject.model.enums.RoleType;
import com.mercadolibre.finalProject.util.CreateFakeLogin;
import com.mercadolibre.finalProject.util.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class WarehouseStatusControllerTest extends ControllerTest{
    private static final String BASIC_PATH = "/api/v1";
    private static final String PATH = BASIC_PATH + "/fresh-products/warehouse-status";
    private static final String PATH_WARNING = PATH + "/warning";

    private String token;
    private String tokenInvalid;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    @Qualifier("objectMapper")
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    public void setUp() throws Exception {
        token = CreateFakeLogin.loginValid("onias-rocha", RoleType.REPRESENTATIVE);
        tokenInvalid = CreateFakeLogin.loginInvalid("user", RoleType.REPRESENTATIVE);
    }

    @SneakyThrows
    @Test
    public void shouldGetProductsStatuses () {
        var expectedObject = TestUtils.getProductStatusesResponseDTO();

        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .header("Authorization", token)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].product_id").value(expectedObject.get(0).getProductId()))
                .andExpect(jsonPath("$[0].status_due_date_less_than3_weeks.quantity_batches").value(expectedObject.get(0).getStatusDueDateLessThan3Weeks().getQuantityBatches()))
                .andExpect(jsonPath("$[0].status_due_date_less_than3_weeks.quantity_items").value(expectedObject.get(0).getStatusDueDateLessThan3Weeks().getQuantityItems()))
                .andExpect(jsonPath("$[0].status_due_date_between3_weeks_and2_months.quantity_batches").value(expectedObject.get(0).getStatusDueDateBetween3WeeksAnd2Months().getQuantityBatches()))
                .andExpect(jsonPath("$[0].status_due_date_between3_weeks_and2_months.quantity_items").value(expectedObject.get(0).getStatusDueDateBetween3WeeksAnd2Months().getQuantityItems()))
                .andExpect(jsonPath("$[0].status_due_date_more_than2_months.quantity_batches").value(expectedObject.get(0).getStatusDueDateMoreThan2Months().getQuantityBatches()))
                .andExpect(jsonPath("$[0].status_due_date_more_than2_months.quantity_items").value(expectedObject.get(0).getStatusDueDateMoreThan2Months().getQuantityItems()))
                .andExpect(jsonPath("$[1].product_id").value(expectedObject.get(1).getProductId()))
                .andExpect(jsonPath("$[1].status_due_date_less_than3_weeks.quantity_batches").value(expectedObject.get(1).getStatusDueDateLessThan3Weeks().getQuantityBatches()))
                .andExpect(jsonPath("$[1].status_due_date_less_than3_weeks.quantity_items").value(expectedObject.get(1).getStatusDueDateLessThan3Weeks().getQuantityItems()))
                .andExpect(jsonPath("$[1].status_due_date_between3_weeks_and2_months.quantity_batches").value(expectedObject.get(1).getStatusDueDateBetween3WeeksAnd2Months().getQuantityBatches()))
                .andExpect(jsonPath("$[1].status_due_date_between3_weeks_and2_months.quantity_items").value(expectedObject.get(1).getStatusDueDateBetween3WeeksAnd2Months().getQuantityItems()))
                .andExpect(jsonPath("$[1].status_due_date_more_than2_months.quantity_batches").value(expectedObject.get(1).getStatusDueDateMoreThan2Months().getQuantityBatches()))
                .andExpect(jsonPath("$[1].status_due_date_more_than2_months.quantity_items").value(expectedObject.get(1).getStatusDueDateMoreThan2Months().getQuantityItems()));
    }


    @SneakyThrows
    @Test
    public void shouldGetWarningProductsStatuses () {
        var expectedObject = TestUtils.getProductWarningStatusesResponseDTO();

        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH_WARNING)
                .header("Authorization", token)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].product_id").value(expectedObject.get(0).getProductId()))
                .andExpect(jsonPath("$[0].status_due_date_less_than3_weeks.quantity_batches").value(expectedObject.get(0).getStatusDueDateLessThan3Weeks().getQuantityBatches()))
                .andExpect(jsonPath("$[0].status_due_date_less_than3_weeks.quantity_items").value(expectedObject.get(0).getStatusDueDateLessThan3Weeks().getQuantityItems()));
    }
}
