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
    private String firstname;
    private String middlename;
    private String lastname;
    private String email;
    private String phone;
    private String gender;
    private String dateOfBirth;

    public ProfileRequest(CreateClientRequest CreateClientRequest) {
        this.firstname = CreateClientRequest.getFirstname();
        this.middlename = CreateClientRequest.getMiddlename();
        this.lastname = CreateClientRequest.getLastname();
        this.email = CreateClientRequest.getEmail();
        this.phone = CreateClientRequest.getPhone();
        this.gender = CreateClientRequest.getGender();
        this.dateOfBirth = CreateClientRequest.getDateOfBirth();
    }
}