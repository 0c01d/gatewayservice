package com.gateway.web.wallet.payout;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class PayoutResponse {
    private final Long id;
    private final UUID walletUUID;
    private final BigDecimal value;

    public Long getId() {
        return id;
    }

    public UUID getWalletUUID() {
        return walletUUID;
    }

    public BigDecimal getValue() {
        return value;
    }

    public PayoutResponse(@JsonProperty("id") Long id,
                          @JsonProperty("walletUUID") UUID walletUUID,
                          @JsonProperty("value") BigDecimal value) {
        this.id = id;
        this.walletUUID = walletUUID;
        this.value = value;
    }
}
