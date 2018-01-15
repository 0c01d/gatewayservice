package com.gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.interconnect.AuthGetRequest;
import com.gateway.interconnect.AuthPostRequest;
import com.gateway.interconnect.AuthRequest;
import com.gateway.web.wallet.wallet.WalletResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class WalletService {
    private String apiUrl;
    private StringBuilder tokenContainer;
    private AuthRequest.Credentials credentials;
    private ObjectMapper objectMapper = new ObjectMapper();

    public WalletService() {
        apiUrl = "http://localhost:8081";
        tokenContainer = new StringBuilder();
        credentials = new AuthRequest.Credentials(1, "secret");
    }

    public WalletResponse createWallet() throws UnirestException, IOException {
        AuthPostRequest request = (AuthPostRequest) AuthPostRequest.Builder.request()
                .setUrl(this.apiUrl + "/wallet")
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getObject().toString(), WalletResponse.class);
    }

    public WalletResponse getWallet(UUID uuid) throws UnirestException, IOException {
        AuthGetRequest request = (AuthGetRequest) AuthGetRequest.Builder.request()
                .setUrl(this.apiUrl + "/wallet/" + uuid)
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getObject().toString(), WalletResponse.class);
    }
}
