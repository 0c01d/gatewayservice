package com.gateway.interconnect;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;
import java.util.Map;

public class AuthGetRequest extends AuthRequest {
    private AuthGetRequest(AuthRequest.Credentials credentials,
                           String url,
                           Map<String, Object> query) {
        super(credentials, url, query);
    }

    @Override
    public HttpResponse<JsonNode> send() throws UnirestException {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(this.url)
                .headers(tokenHeader)
                .queryString(query != null ? query : new HashMap<>())
                .asJson();
        if (jsonResponse.getStatus() == 401) {
            HttpResponse<JsonNode> jsonAuthResponse = Unirest.get(this.url)
                    .headers(credentials.getBasicAuthHeader())
                    .queryString(query != null ? query : new HashMap<>())
                    .asJson();
            this.tokenContainer.replace(0, tokenContainer.length(),
                    jsonAuthResponse.getHeaders().get("Auth-Token").get(0));
            return jsonAuthResponse;
        }
        return jsonResponse;
    }

    public static class Builder extends AuthRequest.Builder {

        private Builder() {
            super();
        }

        public static Builder request() {
            return new Builder();
        }

        @Override
        public AuthGetRequest build() {
            return new AuthGetRequest(this.credentials, this.url, this.query);
        }
    }
}
