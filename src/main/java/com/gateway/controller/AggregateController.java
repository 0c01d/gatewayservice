package com.gateway.controller;

import com.gateway.service.AggregateService;
import com.gateway.service.ClientService;
import com.gateway.service.WalletService;
import com.gateway.web.aggregate.AccountResponse;
import com.gateway.web.aggregate.CreateClientRequest;
import com.gateway.web.aggregate.CreateClientResponse;
import com.gateway.web.client.extendedprofile.ExtendedProfileRequest;
import com.gateway.web.client.extendedprofile.ExtendedProfileResponse;
import com.gateway.web.client.profile.ProfileRequest;
import com.gateway.web.client.profile.ProfileResponse;
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
    private final ClientService clientService;
    private final AggregateService aggregateService;

    @Autowired
    public AggregateController(WalletService walletService, ClientService clientService, AggregateService aggregateService) {
        this.walletService = walletService;
        this.clientService = clientService;
        this.aggregateService = aggregateService;
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

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/profile/", method = RequestMethod.POST)
    public ProfileResponse createProfile(@RequestBody ProfileRequest profileRequest) throws UnirestException, IOException {
        return clientService.createProfile(profileRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/profile/{id}", method = RequestMethod.PATCH)
    public ProfileResponse patchProfile(@PathVariable("id") Long id, @RequestBody ProfileRequest profileRequest) throws UnirestException, IOException {
        return clientService.patchProfile(profileRequest, id);
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public ProfileResponse getProfile(@PathVariable("id") Long id) throws UnirestException, IOException {
        return clientService.getProfile(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/profile/{id}", method = RequestMethod.DELETE)
    public void deleteProfile(@PathVariable("id") Long id) throws UnirestException, IOException {
        clientService.deleteProfile(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/profile/extended/", method = RequestMethod.POST)
    public ExtendedProfileResponse createExtendedProfile(@RequestBody ExtendedProfileRequest extendedProfileRequest) throws UnirestException, IOException {
        return clientService.createExtendedProfile(extendedProfileRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/profile/extended/", method = RequestMethod.PATCH)
    public ExtendedProfileResponse patchExtendedProfile(@PathVariable("id") Long id,@RequestBody ExtendedProfileRequest extendedProfileRequest) throws UnirestException, IOException {
        return clientService.patchExtendedProfile(extendedProfileRequest, id);
    }

    @RequestMapping(value = "/profile/extended/{id}", method = RequestMethod.GET)
    public ExtendedProfileResponse getExtendedProfile(@PathVariable("id") Long id) throws UnirestException, IOException {
        return clientService.getExtendedProfile(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/aggr/profile/", method = RequestMethod.POST)
    public CreateClientResponse aggrCreateClient(@RequestBody CreateClientRequest createClientRequest) throws UnirestException, IOException {
        return aggregateService.aggrCreateClient(createClientRequest);
    }

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public List<AccountResponse> getAccount(@PathVariable("id") Long id) throws IOException, UnirestException {
        return aggregateService.getAccount(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/client/{id}", method = RequestMethod.DELETE)
    public void deleteClient(@PathVariable("id") Long id) throws IOException, UnirestException {
        aggregateService.deleteClient(id);
    }

}