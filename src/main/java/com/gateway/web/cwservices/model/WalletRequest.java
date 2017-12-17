package com.gateway.web.cwservices.model;

import com.gateway.web.cwservices.modelaggr.CreateClientRequest;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class WalletRequest {

    Integer balance;
    UUID uuid;

    public WalletRequest(CreateClientRequest createClientRequest) {
        this.balance = createClientRequest.getBalance();
        this.uuid = createClientRequest.getUuid();
    }
}
