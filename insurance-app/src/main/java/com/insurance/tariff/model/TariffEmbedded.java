package com.insurance.tariff.model;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.enums.Packet;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@XmlRootElement(name = "tariff")
@XmlType(name = "tariff", namespace = "/insurance/tariff", propOrder = {"id", "insuranceType", "packet", "price", "active"})
public class TariffEmbedded implements Serializable {

    @Column(name = "tariff_id", nullable = false)
    private Long id;

    @NotNull(message = "Missing insurance type")
    @Enumerated(EnumType.STRING)
    @Column(name = "tariff_type", nullable = false)
    private InsuranceType insuranceType;

    @NotNull(message = "Missing packet")
    @Enumerated(EnumType.STRING)
    @Column(name = "tariff_packet", nullable = false)
    private Packet packet;

    @Digits(integer = 8, fraction = 2)
    @Column(name = "tariff_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Transient
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TariffEmbedded tariff = (TariffEmbedded) o;
        return Objects.equals(id, tariff.id) && insuranceType == tariff.insuranceType && packet == tariff.packet && Objects.equals(price, tariff.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, insuranceType, packet, price);
    }

}
