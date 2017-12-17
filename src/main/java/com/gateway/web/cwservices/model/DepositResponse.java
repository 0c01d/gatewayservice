package com.gateway.web.cwservices.model;

import lombok.Data;

import java.util.UUID;

@Data
public class DepositResponse {

    Integer id;
    UUID uuid;
    Integer value;

}
