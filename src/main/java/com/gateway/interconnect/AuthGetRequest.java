package com.gateway.interconnect;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Map;

public class AuthGetRequest<Body, Response> extends AuthRequest<Body, Response> {
    private AuthGetRequest(AuthRequest.Credentials credentials,
                           String url,
                           Map<String, Object> query,
                           Body body) {
        super(credentials, url, query, body);
    }

    @Override
    public Response send() throws UnirestException {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(this.url)
                .headers(credentials.getBasicAuthHeader())
                .queryString(query)
                .asJson();
        return null;
    }

    public static class Builder<Body, Response> extends AuthRequest.Builder<Body, Response> {

        private Builder() {
            super();
        }

        public static Builder authGetRequest() {
            return new Builder();
        }

        @Override
        public AuthRequest build() {
            return new AuthGetRequest<>(this.credentials, this.url, this.query, this.body);
        }
    }
}
