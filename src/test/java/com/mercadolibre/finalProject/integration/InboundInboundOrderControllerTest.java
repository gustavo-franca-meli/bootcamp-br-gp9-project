package com.mercadolibre.finalProject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.finalProject.dtos.response.AccountResponseDTO;
import com.mercadolibre.finalProject.model.enums.RoleType;
import com.mercadolibre.finalProject.repository.RepresentativeRepository;
import com.mercadolibre.finalProject.util.CreateFakeLogin;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class InboundInboundOrderControllerTest extends ControllerTest {
    private static final String BASIC_PATH = "/api/v1";
    private static final String PATH = BASIC_PATH + "/fresh-products/inboundorder/";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
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

    //POST  CREATE INBOUND ORDER
    @Test
    void shouldCreateInboundOrderDTOCorrectly() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();

        var json = mapper.writeValueAsString(request);

        var expectedBatch = request.getBatchStock().get(0);

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.batch_stock").exists())
                .andExpect(jsonPath("$.batch_stock").isArray())
                .andExpect(jsonPath("$.batch_stock[0].product_id").value(expectedBatch.getProductId()))
                .andExpect(jsonPath("$.batch_stock[0].current_temperature").value(expectedBatch.getCurrentTemperature()))
                .andExpect(jsonPath("$.batch_stock[0].minimum_temperature").value(expectedBatch.getMinimumTemperature()))
                .andExpect(jsonPath("$.batch_stock[0].initial_quantity").value(expectedBatch.getInitialQuantity()))
                .andExpect(jsonPath("$.batch_stock[0].current_quantity").value(expectedBatch.getCurrentQuantity()))
                .andExpect(jsonPath("$.batch_stock[0].manufacturing_date").value(expectedBatch.getManufacturingDate().toString()))
                .andExpect(jsonPath("$.batch_stock[0].manufacturing_time").exists())
                .andExpect(jsonPath("$.batch_stock[0].due_date").value(expectedBatch.getDueDate().toString()))
                .andExpect(jsonPath("$.batch_stock[0].batchNumber").exists());


    }

    @Test
    void shouldCreateInboundOrderDTOCorrectlyWhenSendUpdateJson() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var json = mapper.writeValueAsString(request);
        System.out.println(json);

        var expectedBatch = request.getBatchStock().get(0);

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.batch_stock").exists())
                .andExpect(jsonPath("$.batch_stock").isArray())
                .andExpect(jsonPath("$.batch_stock[0].product_id").value(expectedBatch.getProductId()))
                .andExpect(jsonPath("$.batch_stock[0].current_temperature").value(expectedBatch.getCurrentTemperature()))
                .andExpect(jsonPath("$.batch_stock[0].minimum_temperature").value(expectedBatch.getMinimumTemperature()))
                .andExpect(jsonPath("$.batch_stock[0].initial_quantity").value(expectedBatch.getInitialQuantity()))
                .andExpect(jsonPath("$.batch_stock[0].current_quantity").value(expectedBatch.getCurrentQuantity()))
                .andExpect(jsonPath("$.batch_stock[0].manufacturing_date").value(expectedBatch.getManufacturingDate().toString()))
                .andExpect(jsonPath("$.batch_stock[0].manufacturing_time").exists())
                .andExpect(jsonPath("$.batch_stock[0].due_date").value(expectedBatch.getDueDate().toString()))
                .andExpect(jsonPath("$.batch_stock[0].batchNumber").exists());
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
    void shouldReturnNotFoundWhenWarehouseNotExist() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var invalidWarehouseId = 3L;
        request.getSection().setWarehouseCode(invalidWarehouseId);

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
    void shouldReturnNotFoundWhenSectorNotExistInWarehouse() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var invalidSectorId = 2L;
        request.getSection().setCode(invalidSectorId);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Sector id " + request.getSection().getCode() + " not found in warehouse Id " + request.getSection().getWarehouseCode();

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnAnErrorListOfCreateBatchWhenProductBatchNotRegistered() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var invalidProductId = 50L;
        request.getBatchStock().get(0).setProductId(invalidProductId);
        request.getBatchStock().get(1).setProductId(invalidProductId);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Error in save 2 batch in sector";

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
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] create batch position 0 error: The product doesn't exist. Id: " + invalidProductId));
    }


    @Test
    void shouldReturnAnErrorListOfCreateBatchWhenSectorTypeIsIncompatibleWithProduct() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var productIdIncompatibleWithSectorOne = 2L;
        request.getBatchStock().get(0).setProductId(productIdIncompatibleWithSectorOne);
        request.getBatchStock().get(1).setProductId(productIdIncompatibleWithSectorOne);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Error in save 2 batch in sector";

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").exists())
                .andExpect(jsonPath("$.errors[1].message").exists())
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] create batch position 0 error: Product 2 with type Refrigerated not supported in sector 1"));
    }

    @Test
    void shouldReturnAnErrorListOfCreateBatchWhenSectorNoHasSpaceForBatch() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();

        var stockWithLowCapability = 3L;
        request.getSection().setCode(stockWithLowCapability);

        var json = mapper.writeValueAsString(request);

        var expectedMessage = "Sector 3 doesn't have enough space to new quantity. New quantity 2";

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    /// PUT METHOD UPDATE INBOUND ORDER

    @Test
    @Order(1)
    void shouldReturnInboundOrderDTOWhenUpdateInboundOrderDTOCorrectly() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();

        var jsonUpdate = mapper.writeValueAsString(request);

        var expectedBatch = request.getBatchStock().get(1);

        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdate)
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.order_id").isNumber())
                .andExpect(jsonPath("$.batch_stock").exists())
                .andExpect(jsonPath("$.batch_stock").isArray())
                .andExpect(jsonPath("$.batch_stock[0].product_id").value(expectedBatch.getProductId()))
                .andExpect(jsonPath("$.batch_stock[0].current_temperature").value(expectedBatch.getCurrentTemperature()))
                .andExpect(jsonPath("$.batch_stock[0].minimum_temperature").value(expectedBatch.getMinimumTemperature()))
                .andExpect(jsonPath("$.batch_stock[0].initial_quantity").value(expectedBatch.getInitialQuantity()))
                .andExpect(jsonPath("$.batch_stock[0].current_quantity").value(expectedBatch.getCurrentQuantity()))
                .andExpect(jsonPath("$.batch_stock[0].manufacturing_date").value(expectedBatch.getManufacturingDate().toString()))
                .andExpect(jsonPath("$.batch_stock[0].manufacturing_time").exists())
                .andExpect(jsonPath("$.batch_stock[0].due_date").value(expectedBatch.getDueDate().toString()))
                .andExpect(jsonPath("$.batch_stock[1].batchNumber").value(expectedBatch.getId()));

    }

    @Test
    void shouldReturnErrorWhenOrderIsNotFound() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();
        request.setOrderNumber(400L);

        var json = mapper.writeValueAsString(request);


        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.message").value("Inbound Order Not Found"));

    }

    @Test
    void shouldReturnErrorWhenBatchIdExistInOtherOrder() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();
        var batch = TestUtils.getBatchDTOValidNoId();
        var batches = new ArrayList<>(request.getBatchStock());
        batch.setId(6L);
        batches.add(batch);
        request.setBatchStock(batches);

        var json = mapper.writeValueAsString(request);

        var expectedBatch = request.getBatchStock().get(0);

        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.message").value("Error in save 1 batch in sector"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] create batch position 5 error: this batch id already in use in other order"));

    }

    @Test
    void shouldCreateANewBatchWhenNoHasBatchNumberInBatchStock() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();
        var batch = TestUtils.getBatchDTOValidNoId();
        var batches = new ArrayList<>(request.getBatchStock());
        batch.setId(null);
        batches.add(batch);
        request.setBatchStock(batches);

        var json = mapper.writeValueAsString(request);

        var expectedBatch = request.getBatchStock().get(0);

        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andDo(print())
                .andExpect(jsonPath("$.batch_stock").isArray())
                .andExpect(jsonPath("$.batch_stock[5].batchNumber").exists());

    }

    @Test
    void shouldReturnNotFoundWhenRepresentativeNotWorkInWarehouseInPUTMethod() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();

        var json = mapper.writeValueAsString(request);

        var representativeIdWithNotWorkInWarehouseOne = 2;
        var expectedMessage = "The representative doesn't work in this warehouse. Username: user";

        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("Authorization", tokenInvalid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnNotFoundWhenWarehouseNotExistInPutMethod() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();

        var invalidWarehouseId = 3L;
        request.getSection().setWarehouseCode(invalidWarehouseId);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Warehouse Not Found. Id:" + invalidWarehouseId;

        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnNotFoundWhenSectorNotExistInWarehouseInPutMethod() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();

        var invalidSectorId = 2L;
        request.getSection().setCode(invalidSectorId);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Sector id " + request.getSection().getCode() + " not found in warehouse Id " + request.getSection().getWarehouseCode();

        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnAnErrorListOfCreateBatchWhenProductBatchNotRegisteredInPutMethod() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();

        var invalidProductId = 50L;
        request.getBatchStock().get(0).setProductId(invalidProductId);
        request.getBatchStock().get(1).setProductId(invalidProductId);
        request.getBatchStock().get(2).setProductId(invalidProductId);
        request.getBatchStock().get(3).setProductId(invalidProductId);
        request.getBatchStock().get(4).setProductId(invalidProductId);

        var json = mapper.writeValueAsString(request);

        var expectedMessage = "Error in save 5 batch in sector";

        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").exists())
                .andExpect(jsonPath("$.errors[1].message").exists())
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] create batch position 0 error: The product doesn't exist. Id: " + invalidProductId));
    }

    @Test
    void shouldReturnAnErrorListOfCreateBatchWhenSectorTypeIsIncompatibleWithProductInPutMethod() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var productIdIncompatibleWithSectorOne = 2L;
        request.getBatchStock().get(0).setProductId(productIdIncompatibleWithSectorOne);
        request.getBatchStock().get(1).setProductId(productIdIncompatibleWithSectorOne);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Error in save 2 batch in sector";

        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").exists())
                .andExpect(jsonPath("$.errors[1].message").exists())
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] create batch position 0 error: Product 2 with type Refrigerated not supported in sector 1"));
    }

    @Test
    void shouldReturnAnErrorListOfCreateBatchWhenSectorNoHasSpaceForBatchInPutMethod() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();


        var stockWithLowCapability = 3L;
        request.getSection().setCode(stockWithLowCapability);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Sector 3 doesn't have enough space to new quantity. New quantity 5";

        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }


}

