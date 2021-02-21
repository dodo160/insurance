package com.insurance.model;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tariff", uniqueConstraints = { @UniqueConstraint(columnNames = "insuranceType"),
		@UniqueConstraint(columnNames = "packet"), @UniqueConstraint(columnNames = "price") })
@XmlRootElement()
@XmlType(namespace = "/insurance/model/tariff")
@Where(clause = "deletedDate is null")
public class Tariff extends BaseEntity {

	private static final long serialVersionUID = 1679630365453455649L;

	@Enumerated(EnumType.STRING)
	private InsuranceType insuranceType;

	@Enumerated(EnumType.STRING)
	private Packet packet;

	@Digits(integer = 10, fraction = 2)
	private BigDecimal price;

	@OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Insurance> insurances = new HashSet<>();

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

	@XmlTransient
	public Set<Insurance> getInsurances() {
		return insurances;
	}

	public void setInsurances(Set<Insurance> insurances) {
		this.insurances = insurances;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Tariff)) return false;
		if (!super.equals(o)) return false;
		Tariff tariff = (Tariff) o;
		return getInsuranceType() == tariff.getInsuranceType() &&
				getPacket() == tariff.getPacket() &&
				Objects.equals(getPrice(), tariff.getPrice());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getInsuranceType(), getPacket(), getPrice());
	}

	@Override
	public String toString() {
		return "Tariff{" +
				"insuranceType=" + insuranceType +
				", packet=" + packet +
				", price=" + price +
				"} " + super.toString();
	}
}
