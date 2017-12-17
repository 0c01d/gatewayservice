package com.gateway.web.cwservices.modelaggr;

import com.gateway.web.cwservices.model.AccountResponse;
import com.gateway.web.cwservices.model.DepositResponse;
import com.gateway.web.cwservices.model.PayoutResponse;
import lombok.Data;

import java.util.UUID;

@Data
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
}