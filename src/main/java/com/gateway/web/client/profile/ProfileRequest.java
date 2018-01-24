package com.gateway.web.client.profile;

import com.gateway.web.aggregate.CreateClientRequest;
import com.gateway.web.wallet.wallet.WalletResponse;

import java.util.UUID;

public class ProfileRequest {

    private String email;
    private String phoneNumber;
    private UUID walletUUID;

    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UUID getWalletUUID() {
        return walletUUID;
    }

    public ProfileRequest(WalletResponse walletResponse,CreateClientRequest createClientRequest){
        this.email = createClientRequest.getEmail();
        this.phoneNumber = createClientRequest.getPhoneNumber();
        this.walletUUID = walletResponse.getWalletUUID();
    }

   /* public ProfileRequest(CreateClientRequest createClientRequest){
        this.email = createClientRequest.getEmail();
        this.phoneNumber = createClientRequest.getPhoneNumber();
    }*/
}
