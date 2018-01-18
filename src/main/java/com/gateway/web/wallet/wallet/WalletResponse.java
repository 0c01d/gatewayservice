package com.gateway.web.wallet.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletResponse {
    private final UUID uuid;
    private final BigDecimal balance;

    public UUID getUuid() {
        return uuid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public WalletResponse(@JsonProperty("walletUUID") UUID uuid,
                          @JsonProperty("value") BigDecimal balance) {
        this.uuid = uuid;
        this.balance = balance;
    }
}
