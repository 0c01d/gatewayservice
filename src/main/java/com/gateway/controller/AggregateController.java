package com.gateway.controller;

import com.gateway.service.WalletService;
import com.gateway.web.wallet.wallet.WalletResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/wallet/", method = RequestMethod.POST)
    public WalletResponse createWallet(@RequestBody(required = false) String ignored) throws UnirestException, IOException {
        return walletService.createWallet();
    }

    @RequestMapping(value = "/wallet/{uuid}", method = RequestMethod.GET)
    public WalletResponse getWallet(@PathVariable("uuid") UUID uuid) throws UnirestException, IOException {
        return walletService.getWallet(uuid);
    }
}