package com.gateway.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.interconnect.AuthDeleteRequest;
import com.gateway.interconnect.AuthGetRequest;
import com.gateway.interconnect.AuthPostRequest;
import com.gateway.interconnect.AuthRequest;
import com.gateway.web.wallet.deposit.DepositRequest;
import com.gateway.web.wallet.deposit.DepositResponse;
import com.gateway.web.wallet.payout.PayoutRequest;
import com.gateway.web.wallet.payout.PayoutResponse;
import com.gateway.web.wallet.wallet.WalletResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void deleteWallet(UUID uuid) throws UnirestException, IOException {
        AuthDeleteRequest request = (AuthDeleteRequest) AuthDeleteRequest.Builder.request()
                .setUrl(this.apiUrl + "/wallet/" + uuid)
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
    }

    public DepositResponse createDeposit(DepositRequest depositRequest) throws UnirestException, IOException {
        AuthPostRequest request = (AuthPostRequest) AuthPostRequest.Builder.request()
                .setBody(objectMapper.writeValueAsString(depositRequest))
                .setUrl(this.apiUrl + "/deposit/")
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getObject().toString(), DepositResponse.class);
    }

    public List<DepositResponse> getDeposits(UUID uuid, Integer page, Integer size) throws UnirestException, IOException {
        Map<String, Object> query = new HashMap<>();
        query.put("page", page != null ? page : 0);
        query.put("size", size != null ? size : 5);
        AuthGetRequest request = (AuthGetRequest) AuthGetRequest.Builder.request()
                .setUrl(this.apiUrl + "/deposit/" + uuid)
                .setQuery(query)
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getArray().toString(),
                new TypeReference<List<DepositResponse>>(){});
    }

    public PayoutResponse createPayout(PayoutRequest payoutRequest) throws UnirestException, IOException {
        AuthPostRequest request = (AuthPostRequest) AuthPostRequest.Builder.request()
                .setBody(objectMapper.writeValueAsString(payoutRequest))
                .setUrl(this.apiUrl + "/payout/")
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getObject().toString(), PayoutResponse.class);
    }

    public List<PayoutResponse> getPayouts(UUID uuid, Integer page, Integer size) throws UnirestException, IOException {
        Map<String, Object> query = new HashMap<>();
        query.put("page", page != null ? page : 0);
        query.put("size", size != null ? size : 5);
        AuthGetRequest request = (AuthGetRequest) AuthGetRequest.Builder.request()
                .setUrl(this.apiUrl + "/payout/" + uuid)
                .setQuery(query)
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getArray().toString(),
                new TypeReference<List<PayoutResponse>>(){});
    }
}
