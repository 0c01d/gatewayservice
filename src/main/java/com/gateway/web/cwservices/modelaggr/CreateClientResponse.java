package com.gateway.web.cwservices.modelaggr;

import com.gateway.web.cwservices.model.AccountResponse;
import com.gateway.web.cwservices.model.ProfileResponse;
import com.gateway.web.cwservices.model.WalletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateClientResponse {
    private Integer profileId;
    private String firstname;
    private String middlename;
    private String lastname;
    private String email;
    private String phone;
    private String gender;
    private String dateOfBirth;

    private UUID walletUUID;
    private Integer balance;

    private Integer accountId;
    private String nickname;



    public CreateClientResponse(WalletResponse walletResponse, ProfileResponse profileResponse, AccountResponse accountResponse) {
        this.walletUUID = walletResponse.getUuid();
        this.balance = walletResponse.getBalance();

        this.profileId = profileResponse.getId();
        this.firstname = profileResponse.getFirstname();
        this.middlename = profileResponse.getMiddlename();
        this.lastname = profileResponse.getLastname();
        this.email = profileResponse.getEmail();
        this.phone = profileResponse.getPhone();
        this.gender = profileResponse.getGender();
        this.dateOfBirth =profileResponse.getDateOfBirth();

        this.accountId = accountResponse.getId();
        this.nickname = accountResponse.getNickname();
    }
}