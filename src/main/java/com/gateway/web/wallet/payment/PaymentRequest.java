package com.gateway.web.wallet.payment;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentRequest {
    private final UUID walletUUID;
    private final BigDecimal value;

    public UUID getWalletUUID() {
        return walletUUID;
    }

    public BigDecimal getValue() {
        return value;
    }

    public PaymentRequest(UUID walletUUID, BigDecimal value) {
        this.walletUUID = walletUUID;
        this.value = value;
    }
}
