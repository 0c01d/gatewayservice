package com.gateway.web.cwservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Integer id;
    private String nickname;
    private Integer profileId;
    private UUID walletUUID;

}