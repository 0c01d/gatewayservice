package com.gateway.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.queue.Queue;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.print.Pageable;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@Service
public class CWService {

   private String URI = ("http://localhost:8765/");

    private static final Logger logger = LoggerFactory.getLogger(RouteFilter.class);

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);

    @Autowired
    Queue queue;

    public CreateClientResponse createClient(CreateClientRequest createClientRequest) throws UnirestException {

        try {
            HttpResponse<JsonNode> walletResponseRaw = Unirest.post(URI + "wallet-service/wallet")
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .body(objectMapper.writeValueAsString(""))
                    .asJson();

            WalletResponse walletResponse = objectMapper.readValue(walletResponseRaw.getBody().getObject().toString(), WalletResponse.class);
            try {
                ProfileRequest profileRequest = new ProfileRequest(createClientRequest);
                HttpResponse<JsonNode> profileResponseRaw = Unirest.post( URI + "client-service/profile")
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .body(objectMapper.writeValueAsString(profileRequest))
                        .asJson();
                ProfileResponse profileResponse = objectMapper.readValue(profileResponseRaw.getBody().getObject().toString(), ProfileResponse.class);

                try {
                    AccountRequest accountRequest = new AccountRequest(createClientRequest, walletResponse, profileResponse);
                    HttpResponse<JsonNode> accountResponseRaw = Unirest.post(URI + "client-service/account")
                            .header("accept", "application/json")
                            .header("content-type", "application/json")
                            .body(objectMapper.writeValueAsString(accountRequest))
                            .asJson();
                    AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);

                    return new CreateClientResponse(walletResponse, profileResponse, accountResponse);
                } catch (IOException e) {

                    HttpRequestWithBody deleteProfile = Unirest.delete(URI + "client-service/profile/" + profileResponse.getId());
                    deleteProfile.asJson();

                    HttpRequestWithBody deleteWallet = Unirest.delete( URI + "wallet-service/wallet/" + walletResponse.getUuid());
                    deleteWallet.asJson();
                    return null;
                }

            } catch (IOException e) {

                HttpRequestWithBody deleteWallet = Unirest.delete(URI + "wallet-service/wallet/" + walletResponse.getUuid());
                deleteWallet.asJson();
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }


    public List<GetAccountResponse> getAccount(Integer id, Integer page, Integer size) {

        try {
            HttpResponse<JsonNode> accountResponseRaw = Unirest.get(URI + "client-service/account/" + id)
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .asJson();
            AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);
            try {
                HttpResponse<JsonNode> depositResponseRaw = Unirest.get(URI + "wallet-service/deposit/" + accountResponse.getWalletUUID()+ "?page=" + page + "&size=" + size)
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .asJson();
                DepositResponse[] depositResponses = objectMapper.readValue(depositResponseRaw.getBody().getArray().toString(), DepositResponse[].class);
                HttpResponse<JsonNode> payoutResponseRaw = Unirest.get(URI + "wallet-service/payout/" + accountResponse.getWalletUUID()+ "?page=" + page + "&size=" + size)
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .asJson();
                PayoutResponse[] payoutResponses = objectMapper.readValue(payoutResponseRaw.getBody().getArray().toString(), PayoutResponse[].class);

                return Collections.singletonList(new GetAccountResponse(accountResponse, depositResponses, payoutResponses));
            } catch (Exception e) {


                HttpResponse<JsonNode> accountResponseRaw1 = Unirest.get( URI + "client-service/account/" + id)
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .asJson();
                AccountResponse accountResponse1 = objectMapper.readValue(accountResponseRaw1.getBody().getObject().toString(), AccountResponse.class);

                return Collections.singletonList(new GetAccountResponse(accountResponse1));

            }
        } catch (Exception e){
           return null;
        }
    }

    public void deleteClient(Integer id) {

        try {
            HttpResponse<JsonNode> accountResponseRaw = Unirest.get(URI + "client-service/account/" + id)
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .asJson();
            AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);
            try {
                HttpRequestWithBody deleteClient = Unirest.delete(URI + "client-service/profile/" + id);
                deleteClient.asJson();
                try {
                    HttpRequestWithBody deleteWallet = Unirest.delete(URI + "wallet-service/wallet/" + accountResponse.getWalletUUID());
                    deleteWallet.asJson();
                } catch (Exception e) {
                    queue.addTask(() ->
                    {
                        logger.info("TRYING AGAIN");
                        try {
                            HttpRequestWithBody deleteWallet = Unirest.delete(URI + "wallet-service/wallet/" + accountResponse.getWalletUUID());
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
                    logger.info("Retry");
                    try {
                        HttpRequestWithBody deleteClient = Unirest.delete(URI + "account-service/profile/" + id);
                        deleteClient.asJson();

                        HttpRequestWithBody deleteWallet = Unirest.delete(URI + "wallet-service/wallet/" + accountResponse.getWalletUUID());
                        deleteWallet.asJson();

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
                    HttpResponse<JsonNode> accountResponseRaw = Unirest.get(URI + "client-service/account/" + id)
                            .header("accept", "application/json")
                            .header("content-type", "application/json")
                            .asJson();
                    AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);

                    HttpRequestWithBody deleteClient = Unirest.delete(URI + "client-service/profile/" + id);
                    deleteClient.asJson();

                    HttpRequestWithBody deleteWallet = Unirest.delete(URI + "wallet-service/wallet/" + accountResponse.getWalletUUID());
                    deleteWallet.asJson();

                } catch (Exception e1) {
                    return false;
                }
                return true;
            });
        }
    }
}













