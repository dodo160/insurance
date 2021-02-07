package com.insurance.validators;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.insurance.enums.InsuranceType;
import com.insurance.model.Insurance;

@Component
public class ValidatorInsuranceDay implements InsuranceValidator {

	@Override
	public InsuranceType getInsuranceType() {
		return InsuranceType.DAY;
	}

	@Override
	public void postValidate(final Insurance insurance) {
		Assert.notNull(insurance.getEndDate(), "Empty EndDate");
		Assert.isTrue(insurance.getStartDate().compareTo(insurance.getEndDate()) <= 0, "EndDate before StartDate");
	}
}
