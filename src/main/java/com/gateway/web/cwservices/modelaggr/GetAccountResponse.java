package com.gateway.web.cwservices.modelaggr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gateway.web.cwservices.model.AccountResponse;
import com.gateway.web.cwservices.model.DepositResponse;
import com.gateway.web.cwservices.model.PayoutResponse;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAccountResponse {

    private Integer accountId;
    private String nickname;
    private UUID uuid;


    public DepositResponse[] deposits;
    public PayoutResponse[] payouts;


    public GetAccountResponse(AccountResponse accountResponse, DepositResponse depositResponse[], PayoutResponse payoutResponse[]) {

        this.accountId = accountResponse.getId();
        this.nickname = accountResponse.getNickname();
        this.uuid = accountResponse.getWalletUUID();


        this.deposits = depositResponse;
        this.payouts = payoutResponse;

    }

    public GetAccountResponse(AccountResponse accountResponse1) {
        this.accountId = accountResponse1.getId();
        this.nickname = accountResponse1.getNickname();
        this.uuid = accountResponse1.getWalletUUID();

    }
}