package com.gateway.web.client.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;


public class ProfileResponse {

    private Long id;
    private String email;
    private String phoneNumber;
    private UUID walletUUID;
    private String username;

    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public UUID getWalletUUID() {
        return walletUUID;
    }
    public String getUsername() {
        return username;
    }

    public ProfileResponse(@JsonProperty("email") String email,
                           @JsonProperty("phoneNumber") String phoneNumber,
                           @JsonProperty("walletUUID") UUID walletUUID,
                           @JsonProperty("username") String username) {

        this.email = email;
        this.phoneNumber = phoneNumber;
        this.walletUUID = walletUUID;
        this.username = username;
    }
}
