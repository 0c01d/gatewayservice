package com.gateway.web.auth.refresh;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public final class RefreshTokenResponse {
    private final String accessToken;
    private final Date accessExpires;

    public String getAccessToken() {
        return accessToken;
    }

    public Date getAccessExpires() {
        return accessExpires;
    }

    public RefreshTokenResponse(@JsonProperty("accessToken") String accessToken,
                                @JsonProperty("accessExpires") Date accessExpires) {
        this.accessToken = accessToken;
        this.accessExpires = accessExpires;
    }
}
