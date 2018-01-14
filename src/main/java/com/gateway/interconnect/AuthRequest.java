package com.gateway.interconnect;

import com.mashape.unirest.http.exceptions.UnirestException;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;

public abstract class AuthRequest<Body, Response> {
    final Credentials credentials;
    final String url;
    final Map<String, Object> query;
    final Body body;

    public abstract Response send() throws UnirestException;

    AuthRequest(Credentials credentials, String url, Map<String, Object> query, Body body) {
        this.credentials = credentials;
        this.url = url;
        this.query = query;
        this.body = body;
    }

    public static final class Credentials {
        private final Integer id;
        private final String secret;

        public Map<String, String> getBasicAuthHeader() {
            String credentials = id.toString() + ":" + secret;
            Map<String, String> authorization = new HashMap<>();
            authorization.put("Authorization", DatatypeConverter.printBase64Binary(credentials.getBytes()));
            return authorization;
        }

        public Credentials(Integer id, String secret) {
            this.id = id;
            this.secret = secret;
        }
    }


    public static abstract class Builder<Body, Response> {
        Credentials credentials;
        String url;
        Map<String, Object> query;
        Body body;

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

        public Builder setBody(Body body) {
            this.body = body;
            return this;
        }

        public abstract AuthRequest build();
    }
}
