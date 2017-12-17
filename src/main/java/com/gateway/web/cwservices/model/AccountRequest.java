package com.gateway.web.cwservices.model;

import com.gateway.web.cwservices.modelaggr.CreateClientRequest;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(chain = true)
@Data
public class AccountRequest {
    private String nickname;
    private UUID walletUUID;
    private Integer profileId;

    public AccountRequest(CreateClientRequest CreateClientRequest, WalletResponse walletResponse, ProfileResponse profileResponse) {
        this.nickname = CreateClientRequest.getNickname();
        this.walletUUID = walletResponse.getUuid();
        this.profileId = profileResponse.getId();
    }
}
