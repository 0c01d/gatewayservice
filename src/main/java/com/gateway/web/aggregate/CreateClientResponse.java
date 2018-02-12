package com.gateway.web.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gateway.web.client.extendedprofile.ExtendedProfileResponse;
import com.gateway.web.client.profile.ProfileResponse;
import com.gateway.web.wallet.wallet.WalletResponse;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateClientResponse {

    private UUID walletUUID;
    private BigDecimal balance;

    private String email;
    private String phoneNumber;
    private String username;


    private Long id;
    private Long profileId;
    private String firstname;
    private String middlename;
    private String lastname;
    private String gender;
    private String dateOfBirth;

    public UUID getWalletUUID() {
        return walletUUID;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }
    public Long getProfileId() {
        return profileId;
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

    public CreateClientResponse(WalletResponse walletResponse, ProfileResponse profileResponse, ExtendedProfileResponse extendedProfileResponse){

        this.walletUUID = walletResponse.getWalletUUID();
        this.balance = walletResponse.getBalance();

        this.email = profileResponse.getEmail();
        this.phoneNumber = profileResponse.getPhoneNumber();
        this.username = profileResponse.getUsername();

        this.id = extendedProfileResponse.getId();
        this.profileId = extendedProfileResponse.getProfileId();
        this.firstname = extendedProfileResponse.getFirstname();
        this.middlename = extendedProfileResponse.getMiddlename();
        this.lastname = extendedProfileResponse.getLastname();
        this.gender = extendedProfileResponse.getGender();
        this.dateOfBirth = extendedProfileResponse.getDateOfBirth();
    }
}
