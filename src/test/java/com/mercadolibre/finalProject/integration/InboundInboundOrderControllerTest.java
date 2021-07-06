package com.mercadolibre.finalProject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.finalProject.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class InboundInboundOrderControllerTest extends ControllerTest{
    private static final String PATH = "/api/v1/fresh-products/inboundorder/";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Autowired
    MockMvc mockMvc;
    @Autowired@Qualifier("objectMapper")
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    //POST METHOD CREATE INBOUND ORDER
    @Test
    void shouldCreateInboundOrderDTOCorrectly() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var json = mapper.writeValueAsString(request);
        System.out.println(json);

        var expectedBatch = request.getBatchStock().get(0);

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("X-Representative-Id","1")
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
                .andExpect(jsonPath("$.batch_stock[0].batchNumber").value(expectedBatch.getId()));


    }
    @Test
    void shouldReturnNotFoundWhenRepresentativeNotWorkInWarehouse() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var json = mapper.writeValueAsString(request);
        System.out.println(json);

        var representativeIdWithNotWorkInWarehouseOne = 2;
        var expectedMessage = "The representative doesn't work in this warehouse. Id: " + representativeIdWithNotWorkInWarehouseOne;

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("X-Representative-Id",representativeIdWithNotWorkInWarehouseOne)
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
                .header("X-Representative-Id",representativeId)
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

        var expectedMessage = "Sector id " + request.getSection().getCode() +" not found in warehouse Id " + request.getSection().getWarehouseCode();

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("X-Representative-Id",representativeId)
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

        var invalidProductId = 2L;
        request.getBatchStock().get(0).setProductId(invalidProductId);
        request.getBatchStock().get(1).setProductId(invalidProductId);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Error in save 2 bath in sector";

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("X-Representative-Id",representativeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").exists())
                .andExpect(jsonPath("$.errors[1].message").exists())
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] create batch id 1 error: Product Not Found Exception"));
    }


    @Test
    void shouldReturnAnErrorListOfCreateBatchWhenSectorTypeIsIncompatibleWithProduct() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var productIdIncompatibleWithSectorOne = 3L;
        request.getBatchStock().get(0).setProductId(productIdIncompatibleWithSectorOne);
        request.getBatchStock().get(1).setProductId(productIdIncompatibleWithSectorOne);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Error in save 2 bath in sector";

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("X-Representative-Id",representativeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").exists())
                .andExpect(jsonPath("$.errors[1].message").exists())
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] create batch id 1 error: Product 3 with  TypeNot Perishable not supported in sector 1"));
    }
    @Test
    void shouldReturnAnErrorListOfCreateBatchWhenSectorNoHasSpaceForBatch() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));



        var stockWithLowCapability = 3L;
        request.getSection().setCode(stockWithLowCapability);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Error in save 1 bath in sector";

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("X-Representative-Id",representativeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").exists())
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] create batch id 1 error: Sector 3 doesn't have enough space"));
    }

   /// PUT METHOD UPDATE INBOUND ORDER

    @Test
    void shouldUpdateInboundOrderDTOCorrectly() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForUpdate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var json = mapper.writeValueAsString(request);

        var expectedBatch = request.getBatchStock().get(0);

        this.mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .header("X-Representative-Id","1")
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
                .andExpect(jsonPath("$.batch_stock[0].batchNumber").value(expectedBatch.getId()));


    }
    @Test
    void shouldReturnNotFoundWhenRepresentativeNotWorkInWarehouse() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var json = mapper.writeValueAsString(request);
        System.out.println(json);

        var representativeIdWithNotWorkInWarehouseOne = 2;
        var expectedMessage = "The representative doesn't work in this warehouse. Id: " + representativeIdWithNotWorkInWarehouseOne;

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("X-Representative-Id",representativeIdWithNotWorkInWarehouseOne)
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
                .header("X-Representative-Id",representativeId)
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

        var expectedMessage = "Sector id " + request.getSection().getCode() +" not found in warehouse Id " + request.getSection().getWarehouseCode();

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("X-Representative-Id",representativeId)
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

        var invalidProductId = 2L;
        request.getBatchStock().get(0).setProductId(invalidProductId);
        request.getBatchStock().get(1).setProductId(invalidProductId);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Error in save 2 bath in sector";

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("X-Representative-Id",representativeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").exists())
                .andExpect(jsonPath("$.errors[1].message").exists())
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] create batch id 1 error: Product Not Found Exception"));
    }


    @Test
    void shouldReturnAnErrorListOfCreateBatchWhenSectorTypeIsIncompatibleWithProduct() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var productIdIncompatibleWithSectorOne = 3L;
        request.getBatchStock().get(0).setProductId(productIdIncompatibleWithSectorOne);
        request.getBatchStock().get(1).setProductId(productIdIncompatibleWithSectorOne);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Error in save 2 bath in sector";

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("X-Representative-Id",representativeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").exists())
                .andExpect(jsonPath("$.errors[1].message").exists())
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] create batch id 1 error: Product 3 with  TypeNot Perishable not supported in sector 1"));
    }
    @Test
    void shouldReturnAnErrorListOfCreateBatchWhenSectorNoHasSpaceForBatch() throws Exception {
        var request = TestUtils.getInboundOrderDTOValidForCreate();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));



        var stockWithLowCapability = 3L;
        request.getSection().setCode(stockWithLowCapability);

        var json = mapper.writeValueAsString(request);

        var representativeId = 1L;

        var expectedMessage = "Error in save 1 bath in sector";

        this.mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .header("X-Representative-Id",representativeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].message").exists())
                .andExpect(jsonPath("$.errors[0].message").value("[ERROR] create batch id 1 error: Sector 3 doesn't have enough space"));
    }




}

