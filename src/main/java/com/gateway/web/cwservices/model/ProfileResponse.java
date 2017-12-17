package com.gateway.web.cwservices.model;

import lombok.Data;

@Data
public class ProfileResponse {
    private Integer id;
    private String firstname;
    private String middlename;
    private String lastname;
    private String email;
    private String phone;
    private String gender;
    private String dateOfBirth;
}
