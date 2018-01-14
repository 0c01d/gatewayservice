package com.gateway.web.cwservices.modelaggr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientRequest {
    private String email;
    private String phoneNumber;
    private String nickname;
    private String password;
}
