package com.insurance.insurance.service.calculator;

import com.insurance.insurance.config.ReinsuranceConfigProperties;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.enums.ReinsuranceType;
import com.insurance.insurance.model.Reinsurance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Service
public class ReinsuranceCalculator {

    private final ReinsuranceConfigProperties reinsuranceConfigProperties;

    public ReinsuranceCalculator(ReinsuranceConfigProperties reinsuranceConfigProperties) {
        this.reinsuranceConfigProperties = reinsuranceConfigProperties;
    }

    public BigDecimal calculateReinsurance(final Collection<Reinsurance> reinsurences, final InsuranceType insuranceType) {
        BigDecimal reinsurancePrice = BigDecimal.ONE;
        if (!reinsurences.isEmpty()) {
            for (Reinsurance r : reinsurences) {
                reinsurancePrice = reinsurancePrice
                        .multiply(getReinsurance(insuranceType, r.getReinsuranceType()));
            }
        }
        return reinsurancePrice;
    }

    private BigDecimal getReinsurance(final InsuranceType insuranceType, final ReinsuranceType reinsuranceType) {
        final BigDecimal value = reinsuranceConfigProperties.get(insuranceType, reinsuranceType);
        return Objects.isNull(value) ? BigDecimal.ONE : value;
    }
}
