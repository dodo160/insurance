package com.insurance.insurance.service.strategy;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Component
public class YearlyInsurancePriceStrategy implements InsurancePriceStrategy {

    @Override
    public BigDecimal calculateInsurance(final Insurance insurance, final BigDecimal reinsurance, final BigDecimal tariffPrice) {
        insurance.setEndDate(insurance.getStartDate().plusYears(1));
        final BigDecimal result = BigDecimal.valueOf(insurance.getPerson()).multiply(tariffPrice);
        return Objects.isNull(reinsurance) || reinsurance.signum() < 1 ? result.setScale(2, RoundingMode.HALF_UP) : result.multiply(reinsurance).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public InsuranceType getInsuranceType() {
        return InsuranceType.YEAR;
    }
}
