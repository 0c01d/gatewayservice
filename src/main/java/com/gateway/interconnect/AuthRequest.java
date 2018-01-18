package com.gateway.interconnect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;

public abstract class AuthRequest {
    final Credentials credentials;
    final String url;
    final Map<String, Object> query;
    StringBuilder tokenContainer;
    Map<String, String> tokenHeader;

    public void setTokenContainer(StringBuilder tokenContainer) {
        this.tokenContainer = tokenContainer;
        tokenHeader.put("Auth-Token", tokenContainer.toString());
    }

    public abstract HttpResponse<JsonNode> send() throws UnirestException, JsonProcessingException;

    AuthRequest(Credentials credentials, String url, Map<String, Object> query) {
        this.credentials = credentials;
        this.url = url;
        this.query = query;
        this.tokenHeader = new HashMap<>();
    }

    public static final class Credentials {
        private final Integer id;
        private final String secret;

        public Map<String, String> getBasicAuthHeader() {
            String credentials = id.toString() + ":" + secret;
            Map<String, String> authorization = new HashMap<>();
            authorization.put("Authorization", "Basic " + DatatypeConverter.printBase64Binary(credentials.getBytes()));
            return authorization;
        }

        public Credentials(Integer id, String secret) {
            this.id = id;
            this.secret = secret;
        }
    }


    public static abstract class Builder {
        Credentials credentials;
        String url;
        Map<String, Object> query;

        Builder() {
        }

        public Builder setCredentials(Credentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setQuery(Map<String, Object> query) {
            this.query = query;
            return this;
        }

        public abstract AuthRequest build();
    }
}
