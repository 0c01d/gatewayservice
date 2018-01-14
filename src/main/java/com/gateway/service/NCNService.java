package com.gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.web.cwservices.model.AccountResponse;
import com.gateway.web.nvnservice.model.ChatNewsRequest;
import com.gateway.web.nvnservice.model.ChatNewsResponse;
import com.gateway.web.nvnservice.model.NewsResponse;
import com.gateway.web.nvnservice.modelaggr.NCNRequest;
import com.gateway.web.nvnservice.modelaggr.NCNResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
public class NCNService {


    private final ObjectMapper objectMapper = new ObjectMapper();

    public NCNResponse createComment(NCNRequest ncnRequest) throws Exception {

        try {
            HttpResponse<JsonNode> accountResponseRaw = Unirest.get("http://localhost:8765//client-service/account/name/" + ncnRequest.getName())
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .asJson();
            AccountResponse accountResponse = objectMapper.readValue(accountResponseRaw.getBody().getObject().toString(), AccountResponse.class);

            try {
                HttpResponse<JsonNode> newsResponseRaw = Unirest.get("http://localhost:8765//news-service/news/" + ncnRequest.getNewsId())
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .asJson();
                NewsResponse newsResponse = objectMapper.readValue(newsResponseRaw.getBody().getObject().toString(), NewsResponse.class);
                try {
                    ChatNewsRequest chatNewsRequest = new ChatNewsRequest(ncnRequest);
                    HttpResponse<JsonNode> chatNewsResponseRaw = Unirest.post("http://localhost:8765//chat-service/chatnews/")
                            .header("accept", "application/json")
                            .header("content-type", "application/json")
                            .body(objectMapper.writeValueAsString(chatNewsRequest))
                            .asJson();
                    ChatNewsResponse chatNewsResponse = objectMapper.readValue(chatNewsResponseRaw.getBody().getObject().toString(), ChatNewsResponse.class);

                    return new NCNResponse(accountResponse, newsResponse, chatNewsResponse);
                } catch (IOException e) {
                    throw new EntityNotFoundException("Sorry, 'Chat-service' server not available now");
                }
            } catch (IOException e) {
                throw new EntityNotFoundException("Sorry, 'News-service' server not available now");
            }
        }catch (IOException e){
            throw new EntityNotFoundException("Sorry, 'Client-service' server not available now");
        }
    }
}
