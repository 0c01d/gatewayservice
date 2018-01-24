package com.gateway.service;

import com.gateway.queue.Queue;
import com.gateway.web.aggregate.AccountResponse;
import com.gateway.web.aggregate.CreateClientRequest;
import com.gateway.web.aggregate.CreateClientResponse;
import com.gateway.web.client.extendedprofile.ExtendedProfileRequest;
import com.gateway.web.client.extendedprofile.ExtendedProfileResponse;
import com.gateway.web.client.profile.ProfileRequest;
import com.gateway.web.client.profile.ProfileResponse;
import com.gateway.web.wallet.deposit.DepositResponse;
import com.gateway.web.wallet.payout.PayoutResponse;
import com.gateway.web.wallet.wallet.WalletResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class AggregateService {



    private final WalletService walletService;
    private final ClientService clientService;

    @Autowired
    public AggregateService(WalletService walletService, ClientService clientService) {
        this.walletService = walletService;
        this.clientService = clientService;
    }
    @Autowired
    Queue queue;


    public CreateClientResponse aggrCreateClient(CreateClientRequest createClientRequest) throws UnirestException, IOException {
        try{
            WalletResponse walletResponse = walletService.createWallet();

            try {
                ProfileRequest profileRequest = new ProfileRequest(walletResponse,createClientRequest);
                ProfileResponse profileResponse = clientService.createProfile(profileRequest);
                try {

                    ExtendedProfileRequest extendedProfileRequest = new ExtendedProfileRequest(createClientRequest, profileResponse);
                    ExtendedProfileResponse extendedProfileResponse = clientService.createExtendedProfile(extendedProfileRequest);
                    return new CreateClientResponse(walletResponse, profileResponse,extendedProfileResponse);

                } catch (Exception e) {
                    clientService.deleteProfile(profileResponse.getId());
                    walletService.deleteWallet(walletResponse.getWalletUUID());
                }

            } catch (Exception e){
                    walletService.deleteWallet(walletResponse.getWalletUUID());
                }

        } catch (Exception e){

        }
        return null;
    }

    public List<AccountResponse> getAccount(Long id) {

        try {
            ProfileResponse profileResponse = clientService.getProfile(id);
            try {
               List<DepositResponse> depositResponses = walletService.getDeposits(profileResponse.getWalletUUID(), null, null);
              List<PayoutResponse> payoutResponses = walletService.getPayouts(profileResponse.getWalletUUID(), null, null);

                return Collections.singletonList(new AccountResponse(profileResponse, depositResponses, payoutResponses));
            } catch (Exception e) {

                ProfileResponse profileResponse1 = clientService.getProfile(id);


                return Collections.singletonList(new AccountResponse(profileResponse1));

            }
        } catch (Exception e){
            return null;
        }
    }

    public void deleteClient(Long id) {

        try {
            ProfileResponse profileResponse = clientService.getProfile(id);
            try {
                clientService.deleteProfile(id);
                try {
                    walletService.deleteWallet(profileResponse.getWalletUUID());
                } catch (Exception e) {
                    queue.addTask(() ->
                    {
                       /* logger.info("TRYING AGAIN");*/
                        try {
                            walletService.deleteWallet(profileResponse.getWalletUUID());
                        } catch (Exception e1) {
                            return false;
                        }
                        return true;
                    });
                }

            } catch (Exception e) {
                queue.addTask(() ->
                {
                    /*logger.info("Retry");*/
                    try {
                        clientService.deleteProfile(id);

                        walletService.deleteWallet(profileResponse.getWalletUUID());


                    } catch (Exception e1) {
                        return false;
                    }
                    return true;
                });
            }
        }catch (Exception e){
            queue.addTask(() ->
            {
                /*logger.info("TRYING AGAIN");*/
                try {
                    ProfileResponse profileResponse = clientService.getProfile(id);

                    clientService.deleteProfile(id);

                    walletService.deleteWallet(profileResponse.getWalletUUID());


                } catch (Exception e1) {
                    return false;
                }
                return true;
            });
        }
    }

}
