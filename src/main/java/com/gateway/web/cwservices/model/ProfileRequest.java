package com.gateway.web.cwservices.model;

import com.gateway.web.cwservices.modelaggr.CreateClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    private String email;
    private String phoneNumber;

    public ProfileRequest(CreateClientRequest CreateClientRequest) {
        this.email = CreateClientRequest.getEmail();
        this.phoneNumber = CreateClientRequest.getPhoneNumber();
    }
}