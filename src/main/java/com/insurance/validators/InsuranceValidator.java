package com.insurance.validators;

import java.time.LocalDate;

import org.springframework.util.Assert;

import com.insurance.enums.InsuranceType;
import com.insurance.model.Insurance;

public interface InsuranceValidator {

	public InsuranceType getInsuranceType();

	public void postValidate(Insurance insurance);

	public default void validate(final Insurance insurance) {
		Assert.notNull(insurance, "Missing Insurance");
		Assert.notNull(insurance.getTariff(), "Missing Tariff");
		Assert.notNull(insurance.getTariff().getInsuranceType(), "Empty InsuranceType");
		Assert.notNull(insurance.getTariff().getPrice(), "Missing tariffPrice");
		Assert.notNull(insurance.getStartDate(), "Empty StartDate");
		Assert.isTrue(insurance.getStartDate().compareTo(LocalDate.now()) >= 0, "Date before today");
		Assert.notNull(insurance.getTariff().getPacket(), "Empty Packet");
		Assert.isTrue(insurance.getPerson() >= 1 && insurance.getPerson() <= 3, "Invalid Person range");
		postValidate(insurance);
	}
}
