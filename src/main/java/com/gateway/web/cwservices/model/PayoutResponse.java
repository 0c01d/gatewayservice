package com.gateway.web.cwservices.model;

import lombok.Data;

import java.util.UUID;

@Data
public class PayoutResponse {

    Integer id;
    UUID uuid;
    Integer value;
}
