package com.insurance.insurance.service.strategy;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;

import java.math.BigDecimal;

public interface InsurancePriceStrategy {

    public BigDecimal calculateInsurance(Insurance insurance, BigDecimal reinsurance, BigDecimal tariffPrice);

    public InsuranceType getInsuranceType();
}
