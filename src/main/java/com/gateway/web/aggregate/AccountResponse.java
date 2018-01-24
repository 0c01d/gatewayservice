package com.gateway.web.aggregate;

import com.gateway.web.client.profile.ProfileResponse;
import com.gateway.web.wallet.deposit.DepositResponse;
import com.gateway.web.wallet.payout.PayoutResponse;

import java.util.List;
import java.util.UUID;

public class AccountResponse {

    private Long id;
    private String email;
    private String phoneNumber;
    private UUID walletUUID;
    private List<DepositResponse> depositResponses;
    private List<PayoutResponse> payoutResponses;

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

    public List<DepositResponse> getDepositResponses() {
        return depositResponses;
    }
    public List<PayoutResponse> getPayoutResponses() {
        return payoutResponses;
    }

    public AccountResponse(ProfileResponse profileResponse, List<DepositResponse> depositResponses, List<PayoutResponse> payoutResponses){
        this.id = profileResponse.getId();
        this.email = profileResponse.getEmail();
        this.phoneNumber = profileResponse.getPhoneNumber();
        this.walletUUID = profileResponse.getWalletUUID();
        this.depositResponses = depositResponses;
        this.payoutResponses = payoutResponses;
    }

    public AccountResponse(ProfileResponse profileResponse1){
        this.id = profileResponse1.getId();
        this.email = profileResponse1.getEmail();
        this.phoneNumber = profileResponse1.getPhoneNumber();
        this.walletUUID = profileResponse1.getWalletUUID();
    }
}
