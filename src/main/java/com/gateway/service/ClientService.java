package com.gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.interconnect.*;
import com.gateway.web.client.extendedprofile.ExtendedProfileRequest;
import com.gateway.web.client.extendedprofile.ExtendedProfileResponse;
import com.gateway.web.client.profile.ProfileRequest;
import com.gateway.web.client.profile.ProfileResponse;
import com.gateway.web.wallet.wallet.WalletResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class ClientService {

    private String apiUrl;
    private StringBuilder tokenContainer;
    private AuthRequest.Credentials credentials;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ClientService() {
        apiUrl = "http://localhost:8082";
        tokenContainer = new StringBuilder();
        credentials = new AuthRequest.Credentials(1, "secret");
    }

    public ProfileResponse createProfile(ProfileRequest profileRequest) throws UnirestException, IOException {
        AuthPostRequest request = (AuthPostRequest) AuthPostRequest.Builder.request()
                .setBody(objectMapper.writeValueAsString(profileRequest))
                .setUrl(this.apiUrl + "/profile/")
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getObject().toString(), ProfileResponse.class);
    }

    public ProfileResponse getProfile(Long id) throws UnirestException, IOException {
        AuthGetRequest request = (AuthGetRequest) AuthGetRequest.Builder.request()
                .setUrl(this.apiUrl + "/profile/" + id)
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getObject().toString(), ProfileResponse.class);
    }

    public void deleteProfile(Long id) throws UnirestException, IOException {
        AuthDeleteRequest request = (AuthDeleteRequest) AuthDeleteRequest.Builder.request()
                .setUrl(this.apiUrl + "/profile/" + id)
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
    }

    public ExtendedProfileResponse createExtendedProfile(ExtendedProfileRequest extendedProfileRequest) throws UnirestException, IOException {
        AuthPostRequest request = (AuthPostRequest) AuthPostRequest.Builder.request()
                .setBody(objectMapper.writeValueAsString(extendedProfileRequest))
                .setUrl(this.apiUrl + "/profile/extended/")
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getObject().toString(), ExtendedProfileResponse.class);
    }

    public ExtendedProfileResponse getExtendedProfile(Long id) throws UnirestException, IOException {
        AuthGetRequest request = (AuthGetRequest) AuthGetRequest.Builder.request()
                .setUrl(this.apiUrl + "/profile/extended/" + id)
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getObject().toString(), ExtendedProfileResponse.class);
    }

    public ProfileResponse patchProfile(ProfileRequest profileRequest, Long id) throws UnirestException, IOException {
        AuthPatchRequest request = (AuthPatchRequest) AuthPatchRequest.Builder.request()
                .setBody(objectMapper.writeValueAsString(profileRequest))
                .setUrl(this.apiUrl + "/profile/" + id)
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getObject().toString(), ProfileResponse.class);
    }

    public ExtendedProfileResponse patchExtendedProfile(ExtendedProfileRequest extendedProfileRequest, Long id) throws UnirestException, IOException {
        AuthPatchRequest request = (AuthPatchRequest) AuthPatchRequest.Builder.request()
                .setBody(objectMapper.writeValueAsString(extendedProfileRequest))
                .setUrl(this.apiUrl + "/profile/extended/"+ id)
                .setCredentials(credentials)
                .build();
        request.setTokenContainer(tokenContainer);
        HttpResponse<JsonNode> response = request.send();
        return objectMapper.readValue(response.getBody().getObject().toString(), ExtendedProfileResponse.class);
    }

}
