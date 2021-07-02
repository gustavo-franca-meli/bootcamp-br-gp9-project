package com.mercadolibre.finalProject.integration;

import com.mercadolibre.finalProject.util.faker.InboundOrderFaker;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InboundOrderControllerTest extends ControllerTest{
   /* @Test
    void ping() {

        HttpHeaders headers = new HttpHeaders();

        var body = InboundOrderFaker.getValidInboundOrderRequest();

        var request =  new RequestEntity<>(body,headers, HttpMethod.POST, null);

        ResponseEntity<String> responseEntity = this.testRestTemplate.exchange("/ping", HttpMethod.POST,request, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("pong", responseEntity.getBody());
    }*/
}

