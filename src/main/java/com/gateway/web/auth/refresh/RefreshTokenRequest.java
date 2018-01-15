package com.gateway.web.auth.refresh;

public final class RefreshTokenRequest {
    private final String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
