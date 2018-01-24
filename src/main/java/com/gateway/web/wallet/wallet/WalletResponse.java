package com.gateway.web.wallet.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletResponse {
    private final UUID walletUUID;
    private final BigDecimal balance;

    public UUID getWalletUUID() {
        return walletUUID;
    }
    public BigDecimal getBalance() {
        return balance;
    }

    public WalletResponse(@JsonProperty("walletUUID") UUID walletUUID,
                          @JsonProperty("value") BigDecimal balance) {
        this.walletUUID = walletUUID;
        this.balance = balance;
    }
}
