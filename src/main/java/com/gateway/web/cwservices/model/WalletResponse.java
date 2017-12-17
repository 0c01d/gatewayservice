package com.gateway.web.cwservices.model;

import lombok.Data;

import java.util.UUID;

@Data
public class WalletResponse {
    private UUID uuid;
    private Integer balance;
}
