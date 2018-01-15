package com.gateway.web.auth.signin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public final class SignInResponse {
    private final String accessToken;
    private final Date accessExpire;
    private final String refreshToken;
    private final Date refreshExpire;

    public String getAccessToken() {
        return accessToken;
    }

    public Date getAccessExpire() {
        return accessExpire;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Date getRefreshExpire() {
        return refreshExpire;
    }

    public SignInResponse(@JsonProperty("accessToken") String accessToken,
                          @JsonProperty("accessExpire") Date accessExpire,
                          @JsonProperty("refreshToken") String refreshToken,
                          @JsonProperty("refreshExpire") Date refreshExpire) {
        this.accessToken = accessToken;
        this.accessExpire = accessExpire;
        this.refreshToken = refreshToken;
        this.refreshExpire = refreshExpire;
    }
}
