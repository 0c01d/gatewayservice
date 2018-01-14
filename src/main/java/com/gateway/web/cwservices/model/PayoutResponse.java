package com.gateway.web.cwservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PayoutResponse {

    Integer id;
    UUID walletUUID;
    Integer value;
}
