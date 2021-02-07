package com.insurance.validators;

import com.insurance.enums.InsuranceType;
import com.insurance.model.Insurance;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ValidatorInsuranceYear implements InsuranceValidator {

	@Override
	public InsuranceType getInsuranceType() {
		return InsuranceType.YEAR;
	}

	@Override
	public void postValidate(final Insurance insurance) {
	}
}
