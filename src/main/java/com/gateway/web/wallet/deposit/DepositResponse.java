package com.gateway.web.wallet.deposit;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class DepositResponse {
    private final Long id;
    private final UUID walletUUID;
    private final BigDecimal value;

    public UUID getWalletUUID() {
        return walletUUID;
    }

    public BigDecimal getValue() {
        return value;
    }

    public DepositResponse(@JsonProperty("id") Long id,
                           @JsonProperty("walletUUID") UUID walletUUID,
                           @JsonProperty("value") BigDecimal value) {
        this.id = id;
        this.walletUUID = walletUUID;
        this.value = value;
    }
}
