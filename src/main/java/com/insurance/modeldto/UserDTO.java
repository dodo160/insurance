package com.insurance.modeldto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.insurance.enums.UserType;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String postCode;
    private String identityId;
    private UserType userType;
    @JsonIgnoreProperties({"tariff","user"})
    private Set<InsuranceDTO> insurances = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Set<InsuranceDTO> getInsurances() {
        return insurances;
    }

    public void setInsurances(Set<InsuranceDTO> insurances) {
        this.insurances = insurances;
    }
}
