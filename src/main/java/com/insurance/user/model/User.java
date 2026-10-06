package com.insurance.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insurance.common.model.AuditEntity;
import com.insurance.insurance.model.Insurance;
import com.insurance.temporal.model.TemporalEntity;
import com.insurance.user.enums.UserType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "identityId")})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)
@XmlRootElement()
@XmlType(namespace = "/insurance/user")
public class User extends AuditEntity {

    private static final long serialVersionUID = 2780692101712032399L;

    @NotBlank(message = "Missing first name")
    private String firstName;
    @NotBlank(message = "Missing last name")
    private String lastName;
    @NotBlank(message = "Missing city")
    private String city;
    @NotBlank(message = "Missing address")
    private String address;
    @NotBlank(message = "Missing post code")
    private String postCode;
    @NotBlank(message = "Missing identity id")
    private String identityId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Insurance> insurances = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<TemporalEntity> temporalEntities = new HashSet<>();

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

    @XmlTransient
    @JsonIgnore
    public Collection<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Collection<Insurance> insurances) {
        this.insurances = insurances;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<TemporalEntity> getTemporalEntities() {
        return temporalEntities;
    }

    public void setTemporalEntities(final Collection<TemporalEntity> temporalEntities) {
        this.temporalEntities = temporalEntities;
    }

    @JsonIgnore
    public UserType getUserType() {
        if (this instanceof Client) {
            return UserType.CLIENT;
        }

        if (this instanceof Employee) {
            return UserType.EMPLOYEE;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getCity(), user.getCity()) &&
                Objects.equals(getAddress(), user.getAddress()) &&
                Objects.equals(getPostCode(), user.getPostCode()) &&
                Objects.equals(getIdentityId(), user.getIdentityId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFirstName(), getLastName(), getCity(), getAddress(), getPostCode(), getIdentityId());
    }

    @Override
    public String toString() {
        return "User{" + super.toString() +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", identityId='" + identityId + '\'' + " }";
    }
}
