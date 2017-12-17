package com.gateway.web.cwservices.model;

import lombok.Data;

import java.util.UUID;

@Data
public class AccountResponse {
    private Integer id;
    private String nickname;
    private Integer profileId;
    private UUID walletUUID;

}