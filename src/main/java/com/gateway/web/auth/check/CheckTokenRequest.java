package com.gateway.web.auth.check;

public final class CheckTokenRequest {
    private final String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public CheckTokenRequest(String accessToken) {
        this.accessToken = accessToken;
    }
}
