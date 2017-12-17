package com.gateway.web.cwservices.model;

import com.gateway.web.cwservices.modelaggr.CreateClientRequest;
import lombok.Data;

@Data
public class WalletRequest {

    public String foo;

    public WalletRequest(CreateClientRequest CreateClientRequest) {
        foo = null;
    }
}
