package com.gateway.web.auth.signout;

public final class SignOutRequest {
    private final String username;

    public String getUsername() {
        return username;
    }

    public SignOutRequest(String username) {
        this.username = username;
    }
}
