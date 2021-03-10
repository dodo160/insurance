package com.insurance.model;

import com.insurance.xml.xmladapter.LocalDateAdapter;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "insurance")
@Where(clause = "deletedDate is null")
@XmlRootElement()
@XmlType(namespace = "/insurance/model/insurance")
public class Insurance extends BaseEntity {

	private static final long serialVersionUID = -2917439034407858747L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tariff_id")
	private Tariff tariff;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull
	@FutureOrPresent
	private LocalDate startDate;

	@Future
	private LocalDate endDate;

	@OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Collection<Reinsurance> reinsurances;

	@Range(min = 1, max = 3)
	@NotNull
	private int person;

	@Digits(integer = 10, fraction = 2)
	private BigDecimal price;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@XmlElementWrapper(name="reinsurances")
	@XmlElement(name="reinsurance")
	public Collection<Reinsurance> getReinsurances() {
		return reinsurances;
	}

	public void setReinsurances(Collection<Reinsurance> reinsurances) {
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

	public Tariff getTariff() {
		return tariff;
	}

	public void setTariff(Tariff tariff) {
		this.tariff = tariff;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void updateReinsurances() {
		reinsurances.forEach(x -> x.setInsurance(this));
	}

	public void addReinsurance(final Reinsurance reinsurance) {
		reinsurance.setInsurance(this);
		reinsurances.add(reinsurance);
	}

	public void removeReinsurance(final Reinsurance reinsurance) {
		reinsurances.remove(reinsurance);
		reinsurance.setInsurance(null);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Insurance)) return false;
		if (!super.equals(o)) return false;
		Insurance insurance = (Insurance) o;
		return getPerson() == insurance.getPerson() &&
				Objects.equals(getTariff(), insurance.getTariff()) &&
				Objects.equals(getUser(), insurance.getUser()) &&
				Objects.equals(getStartDate(), insurance.getStartDate()) &&
				Objects.equals(getEndDate(), insurance.getEndDate()) &&
				Objects.equals(getPrice(), insurance.getPrice());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getTariff(), getUser(), getStartDate(), getEndDate(), getPerson(), getPrice());
	}

	@Override
	public String toString() {
		return "Insurance{" +
				"tariff=" + tariff +
				", user=" + user +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", reinsurances=" + reinsurances +
				", person=" + person +
				", price=" + price +
				"} " + super.toString();
	}
}
