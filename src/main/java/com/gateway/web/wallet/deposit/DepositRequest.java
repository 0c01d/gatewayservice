package com.gateway.web.wallet.deposit;

import java.math.BigDecimal;
import java.util.UUID;

public class DepositRequest {
    private final UUID walletUUID;
    private final BigDecimal value;

    public UUID getWalletUUID() {
        return walletUUID;
    }

    public BigDecimal getValue() {
        return value;
    }

    public DepositRequest(UUID walletUUID, BigDecimal value) {
        this.walletUUID = walletUUID;
        this.value = value;
    }
}
