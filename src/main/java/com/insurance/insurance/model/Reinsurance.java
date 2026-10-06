package com.insurance.insurance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insurance.common.model.AuditEntity;
import com.insurance.insurance.enums.ReinsuranceType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@Entity
@Table(name = "reinsurance", uniqueConstraints = {@UniqueConstraint(columnNames = "insurance_id"),
        @UniqueConstraint(columnNames = "reinsuranceType")})
@XmlRootElement()
@XmlType(namespace = "/insurance/reinsurance")
public class Reinsurance extends AuditEntity {

    private static final long serialVersionUID = -4854797535664095284L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    @NotNull(message = "Missing reinsurance type")
    @Enumerated(EnumType.STRING)
    private ReinsuranceType reinsuranceType;

    @XmlTransient
    @JsonIgnore
    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public ReinsuranceType getReinsuranceType() {
        return reinsuranceType;
    }

    public void setReinsuranceType(ReinsuranceType reinsuranceType) {
        this.reinsuranceType = reinsuranceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reinsurance)) return false;
        if (!super.equals(o)) return false;
        Reinsurance that = (Reinsurance) o;
        return Objects.equals(getInsurance(), that.getInsurance()) &&
                getReinsuranceType() == that.getReinsuranceType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getInsurance(), getReinsuranceType());
    }

    @Override
    public String toString() {
        return "Reinsurance{" +
                " reinsuranceType=" + reinsuranceType +
                "} " + super.toString();
    }
}
