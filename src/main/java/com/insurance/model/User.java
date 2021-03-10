package com.insurance.model;

import com.insurance.enums.UserType;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "identityId")})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)
@XmlRootElement()
@XmlType(namespace = "/insurance/model/user")
@Where(clause = "deletedDate is null")
public class User extends BaseEntity{

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String city;
    @NotNull
    private String address;
    @NotNull
    private String postCode;
    @NotNull
    private String identityId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Insurance> insurances;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<TemporalEntity> temporalEntities;

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
    public Collection<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Collection<Insurance> insurances) {
        this.insurances = insurances;
    }

    @XmlTransient
    public Collection<TemporalEntity> getTemporalEntities() {
        return temporalEntities;
    }

    public void setTemporalEntities(final Collection<TemporalEntity> temporalEntities) {
        this.temporalEntities = temporalEntities;
    }

    public UserType getUserType(){
        if(this instanceof Client){
            return UserType.CLIENT;
        }

        if(this instanceof Employee){
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
                ", identityId='" + identityId + '\'' + " }" ;
    }
}
