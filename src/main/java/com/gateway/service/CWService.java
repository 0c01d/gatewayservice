package com.gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.web.cwservices.model.*;
import com.gateway.web.cwservices.modelaggr.CreateClientRequest;
import com.gateway.web.cwservices.modelaggr.CreateClientResponse;
import com.gateway.web.cwservices.modelaggr.GetAccountResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class CWService {

    //for convert Json to Object
    private final ObjectMapper objectMapper = new ObjectMapper()/*.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)*/;

//    @Autowired
//    private final ObjectMapper objectMapper;

    public CreateClientResponse createClient(CreateClientRequest createClientRequest) throws IOException, UnirestException {

            WalletRequest walletRequest = new WalletRequest(createClientRequest);
            HttpResponse<JsonNode> walletResponseRaw = Unirest.post("http://localhost:8765//wallet-service/wallet")
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .body(objectMapper.writeValueAsString(walletRequest))
                    .asJson();
            WalletResponse walletResponse = objectMapper.readValue(walletResponseRaw.getBody().getObject().toString(), WalletResponse.class);


            ProfileRequest profileRequest = new ProfileRequest(createClientRequest);
            HttpResponse<JsonNode> profileResponseRaw = Unirest.post("http://localhost:8765//client-service/profile")
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .body(objectMapper.writeValueAsString(profileRequest))
                    .asJson();
            ProfileResponse profileResponse = objectMapper.readValue(profileResponseRaw.getBody().getObject().toString(), ProfileResponse.class);

            AccountRequest accountRequest = new AccountRequest(createClientRequest, walletResponse, profileResponse);
            HttpResponse<JsonNode> accountResponseRaw = Unirest.post("http://localhost:8765//client-service/account")
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .body(objectMapper.writeValueAsString(accountRequest))
                    .asJson();
            AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);

        return new CreateClientResponse(walletResponse, profileResponse, accountResponse);
    }

    public List<GetAccountResponse> getAccount(Integer id, Integer page, Integer size) throws IOException, UnirestException {

        HttpResponse<JsonNode> accountResponseRaw = Unirest.get("http://localhost:8765//client-service/account/" + id)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .asJson();
        AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);

        HttpResponse<JsonNode> depositResponseRaw = Unirest.get("http://localhost:8765//wallet-service/deposit/" + accountResponse.getWalletUUID() + "?page="+ page + "&size=" + size)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .asJson();
        DepositResponse[] depositResponses = objectMapper.readValue(depositResponseRaw.getBody().getArray().toString(), DepositResponse[].class);

        HttpResponse<JsonNode> payoutResponseRaw = Unirest.get("http://localhost:8765//wallet-service/payout/" + accountResponse.getWalletUUID()+ "?page="+ page + "&size=" + size)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .asJson();
        PayoutResponse[] payoutResponses = objectMapper.readValue(payoutResponseRaw.getBody().getArray().toString(), PayoutResponse[].class);

        return Arrays.asList(new GetAccountResponse(accountResponse, depositResponses, payoutResponses));
    }

    public void deleteClient(Integer id) throws IOException, UnirestException {

        HttpResponse<JsonNode> accountResponseRaw = Unirest.get("http://localhost:8765//client-service/account/" + id)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .asJson();
        AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);

        HttpRequestWithBody deleteClient = Unirest.delete("http://localhost:8765//client-service/profile/" + id);
        deleteClient.asJson();

        HttpRequestWithBody deleteWallet = Unirest.delete("http://localhost:8765//wallet-service/wallet/" + accountResponse.getWalletUUID());
        deleteWallet.asJson();
    }
}
