package com.gateway.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.web.cwservices.model.ProfileRequest;
import com.gateway.web.cwservices.model.ProfileResponse;
import com.gateway.web.cwservices.modelaggr.CreateClientRequest;
import com.gateway.web.cwservices.modelaggr.CreateClientResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.javafx.css.CalculatedValue;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DisplayName("GatewayController Test")
public class AggregateControllerTest {

   /* @org.junit.jupiter.api.Test
    void myFirstTest(TestInfo testInfo) {
        assertEquals("no", 2,1);
    }*/

   @Autowired
    private TestRestTemplate restTemplate;

    @org.junit.jupiter.api.Test
    public void createClient(){

        CreateClientRequest clientRequest = new CreateClientRequest()
                .setDateOfBirth("03.09.1993")
                .setEmail("artem-pismo2012@mail.ru")
                .setFirstname("Artem")
                .setGender("Male")
                .setLastname("Goncharov")
                .setMiddlename("Borisovich")
                .setPhone("89654118092")
                .setNickname("Tim");

        CreateClientResponse actualResponse = restTemplate.postForObject("/clients/", clientRequest, CreateClientResponse.class);

        assertEquals("Invalid user response", "Artem", actualResponse.getFirstname());

       /* ProfileRequest profileRequest = new ProfileRequest(clientRequest)
                .setDateOfBirth("03.09.1993")
                .setEmail("artem-pismo2012@mail.ru")
                .setFirstname("Artem")
                .setGender("Male")
                .setLastname("Goncharov")
                .setMiddlename("Borisovich")
                .setPhone("89654118092");

        HttpResponse<JsonNode> clientResponseRaw  = Unirest.post("http://localhost:8765//client-service/profile")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .body(new ObjectMapper().writeValueAsString(profileRequest))
                .asJson();
         ProfileResponse profileResponse = new ObjectMapper().readValue(clientResponseRaw.getBody().getObject().toString(), ProfileResponse.class);
        assertEquals("Invalid user response", clientRequest, profileResponse);*/
    }
}
