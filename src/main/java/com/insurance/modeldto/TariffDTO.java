package com.insurance.modeldto;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;

import java.math.BigDecimal;

public class TariffDTO {

    private Long id;
    private InsuranceType insuranceType;
    private Packet packet;
    private BigDecimal price;

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
}
