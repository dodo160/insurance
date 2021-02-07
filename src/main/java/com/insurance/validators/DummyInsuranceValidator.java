package com.insurance.validators;

import org.springframework.stereotype.Component;

import com.insurance.enums.InsuranceType;
import com.insurance.model.Insurance;

@Component
public class DummyInsuranceValidator implements InsuranceValidator {

	@Override
	public InsuranceType getInsuranceType() {
		return null;
	}

	@Override
	public void postValidate(Insurance insurance) {
		// empty implementation
	}

	@Override
	public void validate(Insurance insurance) {
		// empty implementation
	}

}
