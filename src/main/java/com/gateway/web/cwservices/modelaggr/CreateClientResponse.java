package com.gateway.web.cwservices.modelaggr;

import com.gateway.web.cwservices.model.AccountResponse;
import com.gateway.web.cwservices.model.ProfileResponse;
import com.gateway.web.cwservices.model.WalletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateClientResponse {
    private Integer profileId;
    private String email;
    private String phoneNumber;

    private UUID walletUUID;
    private Integer balance;

    private Integer accountId;
    private String nickname;



    public CreateClientResponse(WalletResponse walletResponse, ProfileResponse profileResponse, AccountResponse accountResponse) {
        this.walletUUID = walletResponse.getUuid();
        this.balance = walletResponse.getBalance();

        this.profileId = profileResponse.getId();
        this.email = profileResponse.getEmail();
        this.phoneNumber = profileResponse.getPhoneNumber();

        this.accountId = accountResponse.getId();
        this.nickname = accountResponse.getNickname();
    }
}