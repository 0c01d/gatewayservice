package com.gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.Queue;
import com.gateway.filter.ErrorFilter;
import com.gateway.filter.PostFilter;
import com.gateway.filter.RouteFilter;
import com.gateway.web.cwservices.model.*;
import com.gateway.web.cwservices.modelaggr.CreateClientRequest;
import com.gateway.web.cwservices.modelaggr.CreateClientResponse;
import com.gateway.web.cwservices.modelaggr.GetAccountResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class CWService {

    private static final Logger logger = LoggerFactory.getLogger(RouteFilter.class);
    //for convert Json to Object
    private final ObjectMapper objectMapper = new ObjectMapper()/*.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)*/;

    @Autowired
    Queue queue;
//    @Autowired
//    private final ObjectMapper objectMapper;

    public CreateClientResponse createClient(CreateClientRequest createClientRequest) throws UnirestException {

        try {
            WalletRequest walletRequest = new WalletRequest(createClientRequest);
            HttpResponse<JsonNode> walletResponseRaw = Unirest.post("http://localhost:8765//wallet-service/wallet")
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .body(objectMapper.writeValueAsString(walletRequest))
                    .asJson();
            WalletResponse walletResponse = objectMapper.readValue(walletResponseRaw.getBody().getObject().toString(), WalletResponse.class);
            try {
                ProfileRequest profileRequest = new ProfileRequest(createClientRequest);
                HttpResponse<JsonNode> profileResponseRaw = Unirest.post("http://localhost:8765//client-service/profile")
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .body(objectMapper.writeValueAsString(profileRequest))
                        .asJson();
                ProfileResponse profileResponse = objectMapper.readValue(profileResponseRaw.getBody().getObject().toString(), ProfileResponse.class);

                try {
                    AccountRequest accountRequest = new AccountRequest(createClientRequest, walletResponse, profileResponse);
                    HttpResponse<JsonNode> accountResponseRaw = Unirest.post("http://localhost:8765//client-service/account")
                            .header("accept", "application/json")
                            .header("content-type", "application/json")
                            .body(objectMapper.writeValueAsString(accountRequest))
                            .asJson();
                    AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);

                    return new CreateClientResponse(walletResponse, profileResponse, accountResponse);
                } catch (IOException e) {

                    HttpRequestWithBody deleteProfile = Unirest.delete("http://localhost:8765//client-service/profile/" + profileResponse.getId());
                    deleteProfile.asJson();

                    HttpRequestWithBody deleteWallet = Unirest.delete("http://localhost:8765//wallet-service/wallet/" + walletResponse.getUuid());
                    deleteWallet.asJson();
                    return null;
                }

            } catch (IOException e) {

                HttpRequestWithBody deleteWallet = Unirest.delete("http://localhost:8765//wallet-service/wallet/" + walletResponse.getUuid());
                deleteWallet.asJson();
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }


    public List<GetAccountResponse> getAccount(Integer id, Integer page, Integer size) throws IOException, UnirestException {

        HttpResponse<JsonNode> accountResponseRaw = Unirest.get("http://localhost:8765//client-service/account/" + id)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .asJson();
        AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);

        HttpResponse<JsonNode> depositResponseRaw = Unirest.get("http://localhost:8765//wallet-service/deposit/" + accountResponse.getWalletUUID() + "?page=" + page + "&size=" + size)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .asJson();
        DepositResponse[] depositResponses = objectMapper.readValue(depositResponseRaw.getBody().getArray().toString(), DepositResponse[].class);

        HttpResponse<JsonNode> payoutResponseRaw = Unirest.get("http://localhost:8765//wallet-service/payout/" + accountResponse.getWalletUUID() + "?page=" + page + "&size=" + size)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .asJson();
        PayoutResponse[] payoutResponses = objectMapper.readValue(payoutResponseRaw.getBody().getArray().toString(), PayoutResponse[].class);

        return Arrays.asList(new GetAccountResponse(accountResponse, depositResponses, payoutResponses));
    }

    public void deleteClient(Integer id) throws IOException {

        try {
            HttpResponse<JsonNode> accountResponseRaw = Unirest.get("http://localhost:8082/account/" + id)
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .asJson();
            AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);
            try {
                HttpRequestWithBody deleteClient = Unirest.delete("http://localhost:8082/profile/" + id);
                deleteClient.asJson();
                try {
                    HttpRequestWithBody deleteWallet = Unirest.delete("http://localhost:8081/wallet/" + accountResponse.getWalletUUID());
                    deleteWallet.asJson();
                } catch (Exception e) {
                    queue.addTask(() ->
                    {
                        logger.info("TRYING AGAIN");
                        try {
                            HttpRequestWithBody deleteWallet = Unirest.delete("http://localhost:8081/wallet/" + accountResponse.getWalletUUID());
                            deleteWallet.asJson();
                        } catch (Exception e1) {
                            return false;
                        }
                        return true;
                    });
                }

            } catch (Exception e) {
                queue.addTask(() ->
                {
                    logger.info("TRYING AGAIN");
                    try {
                        HttpRequestWithBody deleteClient = Unirest.delete("http://localhost:8082/profile/" + id);
                        deleteClient.asJson();
                    } catch (Exception e1) {
                        return false;
                    }
                    return true;
                });
            }
        }catch (Exception e){
            queue.addTask(() ->
            {
                logger.info("TRYING AGAIN");
                try {
                    HttpResponse<JsonNode> accountResponseRaw = Unirest.get("http://localhost:8082/account/" + id)
                            .header("accept", "application/json")
                            .header("content-type", "application/json")
                            .asJson();
                    AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);
                } catch (Exception e1) {
                    return false;
                }
                return true;
            });
        }
    }
}




       /* }  {
            queue.addTask(() ->
            {
                try {
                    HttpResponse<JsonNode> accountResponseRaw = Unirest.get("http://localhost:8765//client-service/account/" + id)
                            .header("accept", "application/json")
                            .header("content-type", "application/json")
                            .asJson();
                    AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);

                    try {
                        HttpRequestWithBody deleteClient = Unirest.delete("http://localhost:8765//client-service/profile/" + id);
                        deleteClient.asJson();

                    try {
                        HttpRequestWithBody deleteWallet = Unirest.delete("http://localhost:8765//wallet-service/wallet/" + accountResponse.getWalletUUID());
                        deleteWallet.asJson();


                } catch (IOException e1) {
                    return false;
                }

                }
                return true;
            });
        }
    }*/












