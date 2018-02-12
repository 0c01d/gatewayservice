package com.gateway.web.aggregate;

import com.gateway.web.client.extendedprofile.ExtendedProfileRequest;
import com.gateway.web.client.profile.ProfileRequest;
import com.gateway.web.client.profile.ProfileResponse;
import com.gateway.web.wallet.wallet.WalletResponse;

import java.util.UUID;

public class CreateClientRequest {

    private String email;
    private String phoneNumber;
    private UUID walletUUID;
    private String username;

    private String firstname;
    private String middlename;
    private String lastname;
    private String gender;
    private String dateOfBirth;
    private Long profileId;

    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getUsername() {
        return username;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getMiddlename() {
        return middlename;
    }
    public String getLastname() {
        return lastname;
    }
    public String getGender() {
        return gender;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public Long getProfileId() {
        return profileId;
    }
    public UUID getWalletUUID() {
        return walletUUID;
    }

    public CreateClientRequest() {}

    public CreateClientRequest(ProfileRequest profileRequest, ExtendedProfileRequest extendedProfileRequest, ProfileResponse profileResponse, WalletResponse walletResponse) {
        this.email = profileRequest.getEmail();
        this.phoneNumber = profileRequest.getPhoneNumber();
        this.username = profileRequest.getUsername();

        this.firstname = extendedProfileRequest.getFirstname();
        this.middlename = extendedProfileRequest.getMiddlename();
        this.lastname = extendedProfileRequest.getLastname();
        this.gender = extendedProfileRequest.getGender();
        this.dateOfBirth = extendedProfileRequest.getDateOfBirth();
        this.profileId = profileResponse.getId();
        this.walletUUID = walletResponse.getWalletUUID();
    }
}
