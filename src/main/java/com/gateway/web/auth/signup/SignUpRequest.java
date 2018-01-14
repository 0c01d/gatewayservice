package com.gateway.web.auth.signup;

public final class SignUpRequest {
    private final String username;
    private final String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public SignUpRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
