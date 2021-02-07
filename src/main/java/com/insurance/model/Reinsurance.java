package com.insurance.model;

import com.insurance.enums.ReinsuranceType;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reinsurance", uniqueConstraints = { @UniqueConstraint(columnNames = "insurance_id"),
		@UniqueConstraint(columnNames = "reinsuranceType") })
@Where(clause = "deletedDate is null")
public class Reinsurance extends BaseEntity {

	private static final long serialVersionUID = -4854797535664095284L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insurance_id")
	private Insurance insurance;

	@Enumerated(EnumType.STRING)
	private ReinsuranceType reinsuranceType;

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
				"insurance=" + insurance +
				", reinsuranceType=" + reinsuranceType +
				"} " + super.toString();
	}
}
