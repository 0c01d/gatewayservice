package com.gateway.controller;

import com.gateway.service.CWService;
import com.gateway.service.NCNService;
import com.gateway.web.cwservices.modelaggr.CreateClientRequest;
import com.gateway.web.cwservices.modelaggr.CreateClientResponse;
import com.gateway.web.cwservices.modelaggr.GetAccountResponse;
import com.gateway.web.nvnservice.modelaggr.NCNRequest;
import com.gateway.web.nvnservice.modelaggr.NCNResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AggregateController {

    private final CWService cwService;
    private final NCNService ncnService;

    @Autowired
    public AggregateController(CWService cwService, NCNService ncnService) {
        this.cwService = cwService;
        this.ncnService = ncnService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/account/profile/wallet", method = RequestMethod.POST)
    public CreateClientResponse createClient(@RequestBody CreateClientRequest createClientRequest) throws IOException, UnirestException {
        return cwService.createClient(createClientRequest);
    }

  /*  @RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
    public List<GetAccountResponse> getClient(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) throws IOException, UnirestException {
        return cwService.getAccount(id, page, size);
    }*/

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public List<GetAccountResponse> getAccount(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) throws IOException, UnirestException {
        return cwService.getAccount(id, page, size);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/client/{id}", method = RequestMethod.POST)
    public void deleteClient(@PathVariable("id") Integer id) throws IOException, UnirestException {
        cwService.deleteClient(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/create/chat/", method = RequestMethod.POST)
    public NCNResponse createComment(@RequestBody NCNRequest ncn) throws Exception {
        return ncnService.createComment(ncn);
    }
}