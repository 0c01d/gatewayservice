package com.gateway.web.client.profile;

import com.gateway.web.aggregate.CreateClientRequest;
import com.gateway.web.wallet.wallet.WalletResponse;

import java.util.UUID;

public class ProfileRequest {

    private String email;
    private String phoneNumber;
    private UUID walletUUID;
    private String username;

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

    public ProfileRequest() {}

    public ProfileRequest(WalletResponse walletResponse, CreateClientRequest createClientRequest){
        this.email = createClientRequest.getEmail();
        this.phoneNumber = createClientRequest.getPhoneNumber();
        this.walletUUID = walletResponse.getWalletUUID();
        this.username = createClientRequest.getUsername();
    }

   /* public ProfileRequest(CreateClientRequest createClientRequest){
        this.email = createClientRequest.getEmail();
        this.phoneNumber = createClientRequest.getPhoneNumber();
    }*/
}
