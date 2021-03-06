package com.insurance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insurance.enums.ReinsuranceType;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@Entity
@Table(name = "reinsurance", uniqueConstraints = { @UniqueConstraint(columnNames = "insurance_id"),
		@UniqueConstraint(columnNames = "reinsuranceType") })
@Where(clause = "deletedDate is null")
@XmlRootElement()
@XmlType(namespace = "/insurance/model/reinsurance")
public class Reinsurance extends BaseEntity {

	private static final long serialVersionUID = -4854797535664095284L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insurance_id")
	private Insurance insurance;

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
