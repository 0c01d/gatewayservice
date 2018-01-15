package com.gateway.web.auth.signin;

public final class SignInRequest {
    private final String username;
    private final String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
