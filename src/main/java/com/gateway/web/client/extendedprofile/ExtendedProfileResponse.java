package com.gateway.web.client.extendedprofile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gateway.web.client.profile.ProfileResponse;

public class ExtendedProfileResponse {

    private Long id;
    private String firstname;
    private String middlename;
    private String lastname;
    private String gender;
    private String dateOfBirth;
    private Long profileId;


    public Long getId() {
        return id;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getMiddlename() {
        return middlename;
    }
    public String getLastname() {
        return lastname;
    }
    public String getGender() {
        return gender;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public Long getProfileId() {
        return profileId;
    }

    public ExtendedProfileResponse(@JsonProperty("firstname") String firstname,
                                   @JsonProperty("middlename") String middlename,
                                   @JsonProperty("lastname") String lastname,
                                   @JsonProperty("gender") String gender,
                                   @JsonProperty("dateOfBirth") String dateOfBirth,
                                   @JsonProperty("profileId") Long profileId){

        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.profileId = profileId ;
    }
}
