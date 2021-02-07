package com.insurance.validators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.insurance.enums.InsuranceType;

@Component
public class ValidatorFactory {

	private final Map<InsuranceType, InsuranceValidator> validators = new HashMap<>();

	@Autowired
	public ValidatorFactory(final List<InsuranceValidator> validators) {
		this.validators.putAll(validators.stream().collect(Collectors.toMap(InsuranceValidator::getInsuranceType, Function.identity())));
	}

	public InsuranceValidator getValidator(final InsuranceType insuranceType) {
		return validators.getOrDefault(insuranceType, new DummyInsuranceValidator());
	}

	public Map<InsuranceType, InsuranceValidator> getValidators() {
		return validators;
	}
}
