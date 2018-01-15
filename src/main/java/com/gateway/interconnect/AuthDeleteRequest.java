package com.gateway.interconnect;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;
import java.util.Map;

public class AuthDeleteRequest extends AuthRequest {
    private AuthDeleteRequest(AuthRequest.Credentials credentials,
                            String url,
                            Map<String, Object> query) {
        super(credentials, url, query);
    }

    @Override
    public HttpResponse<JsonNode> send() throws UnirestException {
        Map<String, String> headers = tokenHeader;
        headers.put("Content-Type", "application/json");
        HttpResponse<JsonNode> jsonResponse = Unirest.delete(this.url)
                .headers(headers)
                .queryString(query != null ? query : new HashMap<>())
                .asJson();
        if (jsonResponse.getStatus() == 401) {
            headers.remove("Auth-Token");
            headers.putAll(credentials.getBasicAuthHeader());
            HttpResponse<JsonNode> jsonAuthResponse = Unirest.delete(this.url)
                    .headers(headers)
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

        public AuthDeleteRequest build() {
            return new AuthDeleteRequest(this.credentials, this.url, this.query);
        }
    }
}
