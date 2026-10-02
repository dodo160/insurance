package com.insurance.tariff.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.insurance.common.model.AuditEntity;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.tariff.enums.Packet;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "tariff")
@XmlRootElement()
@XmlType(namespace = "/insurance/model/tariff")
public class Tariff extends AuditEntity {

    private static final long serialVersionUID = 1679630365453455649L;

    @NotNull(message = "Missing insurance type")
    @Enumerated(EnumType.STRING)
    private InsuranceType insuranceType;

    @NotNull(message = "Missing packet")
    @Enumerated(EnumType.STRING)
    private Packet packet;

    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    private boolean active;

    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Insurance> insurances = new HashSet<>();

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Collection<Insurance> insurances) {
        this.insurances = insurances;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tariff tariff = (Tariff) o;
        return insuranceType == tariff.insuranceType && packet == tariff.packet && Objects.equal(price, tariff.price) && Objects.equal(active, tariff.active);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), insuranceType, packet, price, active);
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "insuranceType=" + insuranceType +
                ", packet=" + packet +
                ", price=" + price +
                ", startDate=" + active +
                "} " + super.toString();
    }
}
