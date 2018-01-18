package com.gateway.interconnect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.web.wallet.deposit.DepositRequest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthPostRequest extends AuthRequest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String body;

    private AuthPostRequest(AuthRequest.Credentials credentials,
                            String url,
                            Map<String, Object> query,
                            String body) {
        super(credentials, url, query);
        this.body = body;
    }

    @Override
    public HttpResponse<JsonNode> send() throws UnirestException {
        Map<String, String> headers = tokenHeader;
        headers.put("Content-Type", "application/json");
        HttpResponse<JsonNode> jsonResponse = Unirest.post(this.url)
                .headers(headers)
                .queryString(query != null ? query : new HashMap<>())
                .body(body != null ? body : "")
                .asJson();
        if (jsonResponse.getStatus() == 401) {
            headers.remove("Auth-Token");
            headers.putAll(credentials.getBasicAuthHeader());
            HttpResponse<JsonNode> jsonAuthResponse = Unirest.post(this.url)
                    .headers(headers)
                    .queryString(query != null ? query : new HashMap<>())
                    .body(body != null ? body : "")
                    .asJson();
            this.tokenContainer.replace(0, tokenContainer.length(),
                    jsonAuthResponse.getHeaders().get("Auth-Token").get(0));
            return jsonAuthResponse;
        }
        return jsonResponse;
    }

    public static class Builder extends AuthRequest.Builder {
        private String body;

        private Builder() {
            super();
        }

        public static Builder request() {
            return new Builder();
        }

        public AuthRequest.Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public AuthPostRequest build() {
            return new AuthPostRequest(this.credentials, this.url, this.query, this.body);
        }
    }
}
