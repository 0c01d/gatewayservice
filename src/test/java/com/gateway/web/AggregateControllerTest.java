package com.gateway.web;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.service.CWService;
import com.gateway.service.NCNService;
import com.gateway.web.cwservices.model.*;
import com.gateway.web.cwservices.modelaggr.CreateClientRequest;
import com.gateway.web.cwservices.modelaggr.CreateClientResponse;
import com.gateway.web.cwservices.modelaggr.GetAccountResponse;
import com.gateway.web.nvnservice.model.ChatNewsRequest;
import com.gateway.web.nvnservice.model.ChatNewsResponse;
import com.gateway.web.nvnservice.model.NewsRequest;
import com.gateway.web.nvnservice.model.NewsResponse;
import com.gateway.web.nvnservice.modelaggr.NCNRequest;
import com.gateway.web.nvnservice.modelaggr.NCNResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AggregateControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CWService cwService;

    @MockBean
    private NCNService ncnService;

    @Test
    public void createClient() throws Exception {

        //Arrange
        WalletRequest walletRequest = new WalletRequest()
                .setBalance(null)
                .setUuid(UUID.randomUUID());

        WalletResponse walletResponse = new WalletResponse()
                .setBalance(0)
                .setUuid(walletRequest.getUuid());

        ProfileRequest profileRequest = new ProfileRequest()
                .setPhone("89654118092")
                .setMiddlename("Borisovich")
                .setLastname("Goncharov")
                .setGender("Male")
                .setEmail("Artem-pismo2012@mail.ru")
                .setDateOfBirth("03.09.1993")
                .setFirstname("Artem");

        ProfileResponse profileResponse = new ProfileResponse()
                .setId(1)
                .setPhone(profileRequest.getPhone())
                .setMiddlename(profileRequest.getMiddlename())
                .setLastname(profileRequest.getLastname())
                .setGender(profileRequest.getGender())
                .setEmail(profileRequest.getEmail())
                .setDateOfBirth(profileRequest.getDateOfBirth())
                .setFirstname(profileRequest.getFirstname());

        AccountRequest accountRequest = new AccountRequest()
                .setNickname("Nickname")
                .setProfileId(profileResponse.getId())
                .setWalletUUID(walletResponse.getUuid());

        AccountResponse accountResponse = new AccountResponse()
                .setId(1)
                .setNickname(accountRequest.getNickname())
                .setProfileId(accountRequest.getProfileId())
                .setWalletUUID(accountRequest.getWalletUUID());


        CreateClientRequest clientRequest = new CreateClientRequest()
                .setDateOfBirth(profileRequest.getDateOfBirth())
                .setEmail(profileRequest.getDateOfBirth())
                .setFirstname(profileRequest.getFirstname())
                .setGender(profileRequest.getGender())
                .setLastname(profileRequest.getLastname())
                .setMiddlename(profileRequest.getMiddlename())
                .setPhone(profileRequest.getPhone())
                .setNickname(accountRequest.getNickname())
                .setBalance(walletRequest.getBalance());

        CreateClientResponse expected = new CreateClientResponse()
                .setProfileId(profileResponse.getId())
                .setBalance(walletResponse.getBalance())
                .setAccountId(accountResponse.getId())
                .setDateOfBirth(clientRequest.getDateOfBirth())
                .setEmail(clientRequest.getEmail())
                .setFirstname(clientRequest.getFirstname())
                .setGender(clientRequest.getGender())
                .setLastname(clientRequest.getLastname())
                .setMiddlename(clientRequest.getMiddlename())
                .setNickname(clientRequest.getNickname())
                .setPhone(clientRequest.getPhone())
                .setWalletUUID(UUID.randomUUID());

        given(cwService.createClient(clientRequest)).willReturn(expected);

        //Act
        MvcResult result = mvc.perform(post("/create/client").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(clientRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        CreateClientResponse actual = mapper.readValue(result.getResponse().getContentAsString(), CreateClientResponse.class);

        //AssertEquals
        assertEquals("Invalid response", expected, actual);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void getAccount() throws Exception {

        AccountResponse accountResponse = new AccountResponse()
                .setId(1)
                .setNickname("Nickname")
                .setProfileId(1)
                .setWalletUUID(UUID.randomUUID());

        DepositResponse[] depositResponses = new DepositResponse[2];
                depositResponses[0] = new DepositResponse()
                        .setWalletUUID(accountResponse.getWalletUUID())
                        .setValue(100)
                        .setId(1);
                depositResponses[1] = new DepositResponse()
                        .setWalletUUID(accountResponse.getWalletUUID())
                        .setValue(50)
                        .setId(2);

        PayoutResponse[] payoutResponses = new PayoutResponse[2];
        payoutResponses[0] = new PayoutResponse()
                .setWalletUUID(accountResponse.getWalletUUID())
                .setValue(100)
                .setId(34);
        payoutResponses[1] = new PayoutResponse()
                .setWalletUUID(accountResponse.getWalletUUID())
                .setValue(50)
                .setId(67);

        //Arrange
         List<GetAccountResponse> expected = new ArrayList<>();
        expected.add(new GetAccountResponse()
                .setAccountId(1)
                .setDeposits(depositResponses)
                .setPayouts(payoutResponses)
                .setNickname(accountResponse.getNickname())
                .setUuid(accountResponse.getWalletUUID()));

        PageRequest pr = new PageRequest(0, 2);
        PageImpl<GetAccountResponse> accountPage = new PageImpl<>(expected, pr, 100);

        given(cwService.getAccount(1, 0, 2)).willReturn((accountPage.getContent()));

        //Act
        MvcResult result = this.mvc.perform(get("/get/account/1?page=0&size=2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<GetAccountResponse> actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<GetAccountResponse>>() {
        });

        //AssertEquals
        assertEquals("Invalid response", expected, actual);
    }

    @Test
    public void deleteClient() throws Exception {

        //Arrange
        AccountResponse accountResponse = new AccountResponse()
                .setId(1)
                .setNickname("Nickname")
                .setProfileId(1)
                .setWalletUUID(UUID.randomUUID());

        //Act
        MvcResult result = mvc.perform(post("/clients/1"))
                .andExpect(status().isNoContent())
                .andReturn();

        //Assert
        verify(cwService, times(1)).deleteClient(1);
    }

    @Test
    public void createComment() throws Exception {


        AccountResponse accountResponse = new AccountResponse()
                .setId(1)
                .setWalletUUID(UUID.randomUUID())
                .setProfileId(1)
                .setNickname("Nickname");

        NewsRequest newsRequest = new NewsRequest()
                .setId(10);

        NewsResponse newsResponse = new NewsResponse()
                .setId(newsRequest.getId())
                .setNewscontent("TestContent");

        ChatNewsRequest chatNewsRequest = new ChatNewsRequest()
                .setNewsId(newsResponse.getId())
                .setComment("TestComment")
                .setName(accountResponse.getNickname());

        ChatNewsResponse chatNewsResponse = new ChatNewsResponse()
                .setNewsId(chatNewsRequest.getNewsId())
                .setComment(chatNewsRequest.getComment())
                .setId(34)
                .setName(accountResponse.getNickname());

        NCNRequest ncnRequest = new NCNRequest()
                .setNewsId(newsResponse.getId())
                .setName(accountResponse.getNickname())
                .setComment(chatNewsResponse.getComment());

        NCNResponse expected = new NCNResponse()
                .setId(chatNewsResponse.getId())
                .setName(ncnRequest.getName())
                .setComment(ncnRequest.getComment())
                .setNewsId(newsResponse.getId());

        given(ncnService.createComment(ncnRequest)).willReturn(expected);

        //Act
        MvcResult result = mvc.perform(post("/create/chat/").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ncnRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        NCNResponse actual = mapper.readValue(result.getResponse().getContentAsString(), NCNResponse.class);

        //AssertEquals
        assertEquals("Invalid response", expected, actual);
    }
}
