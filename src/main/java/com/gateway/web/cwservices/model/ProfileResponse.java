package com.gateway.web.cwservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private Integer id;
    private String email;
    private String phoneNumber;
}
