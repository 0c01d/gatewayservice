package com.gateway.web.cwservices.model;

import com.gateway.web.cwservices.modelaggr.CreateClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String nickname;
    private UUID walletUUID;
    private Integer profileId;
    private String password;

    public AccountRequest(CreateClientRequest createClientRequest, WalletResponse walletResponse, ProfileResponse profileResponse) {
        this.nickname = createClientRequest.getNickname();
        this.walletUUID = walletResponse.getUuid();
        this.profileId = profileResponse.getId();
        this.password = createClientRequest.getPassword();
    }
}
