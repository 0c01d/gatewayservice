package com.gateway.web.auth.check;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CheckTokenResponse {
    private final String username;

    public String getUsername() {
        return username;
    }

    public CheckTokenResponse(@JsonProperty("username") String username) {
        this.username = username;
    }
}
