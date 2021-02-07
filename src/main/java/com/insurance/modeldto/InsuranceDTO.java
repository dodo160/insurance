package com.insurance.modeldto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class InsuranceDTO {

    private Long id;
    private TariffDTO tariff;
    private UserDTO user;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonIgnoreProperties("insurance")
    private Set<ReinsuranceDTO> reinsurances = new HashSet<>();
    private int person;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TariffDTO getTariff() {
        return tariff;
    }

    public void setTariff(TariffDTO tariff) {
        this.tariff = tariff;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<ReinsuranceDTO> getReinsurances() {
        return reinsurances;
    }

    public void setReinsurances(Set<ReinsuranceDTO> reinsurances) {
        this.reinsurances = reinsurances;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
