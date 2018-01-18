package com.gateway.controller;

import com.gateway.service.WalletService;
import com.gateway.web.wallet.deposit.DepositRequest;
import com.gateway.web.wallet.deposit.DepositResponse;
import com.gateway.web.wallet.payout.PayoutRequest;
import com.gateway.web.wallet.payout.PayoutResponse;
import com.gateway.web.wallet.wallet.WalletResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class AggregateController {

    private final WalletService walletService;

    @Autowired
    public AggregateController(WalletService walletService) {
        this.walletService = walletService;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/wallet/", method = RequestMethod.POST)
    public WalletResponse createWallet(@RequestBody(required = false) String ignored) throws UnirestException, IOException {
        return walletService.createWallet();
    }

    @RequestMapping(value = "/wallet/{uuid}", method = RequestMethod.GET)
    public WalletResponse getWallet(@PathVariable("uuid") UUID uuid) throws UnirestException, IOException {
        return walletService.getWallet(uuid);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/wallet/{uuid}", method = RequestMethod.DELETE)
    public void deleteWallet(@PathVariable("uuid") UUID uuid) throws UnirestException, IOException {
         walletService.deleteWallet(uuid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/deposit/", method = RequestMethod.POST)
    public DepositResponse createDeposit(@RequestBody DepositRequest depositRequest) throws UnirestException, IOException {
        return walletService.createDeposit(depositRequest);
    }

    @RequestMapping(value = "/deposit/{uuid}", method = RequestMethod.GET)
    public List<DepositResponse> getListDepositsByUuid(@PathVariable("uuid") UUID uuid,
                                                      @RequestParam(value = "page", required = false) Integer page,
                                                      @RequestParam(value = "size", required = false) Integer size) throws UnirestException, IOException {
        return walletService.getDeposits(uuid, page, size );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/payout/", method = RequestMethod.POST)
    public PayoutResponse createPayout(@RequestBody PayoutRequest payoutRequest) throws UnirestException, IOException {
        return walletService.createPayout(payoutRequest);
    }

    @RequestMapping(value = "/payout/{uuid}", method = RequestMethod.GET)
    public List<PayoutResponse> getListPayoutsByUuid(@PathVariable("uuid") UUID uuid,
                                                      @RequestParam(value = "page", required = false) Integer page,
                                                      @RequestParam(value = "size", required = false) Integer size) throws UnirestException, IOException {
        return walletService.getPayouts(uuid, page, size );
    }

}