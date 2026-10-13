package com.insurance.insurance.service.strategy;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class YearlyInsurancePriceStrategyTest {

    private YearlyInsurancePriceStrategy yearlyInsurancePriceStrategy;

    @BeforeEach
    public void setUp() {
        yearlyInsurancePriceStrategy = new YearlyInsurancePriceStrategy();
    }

    @Test
    public void testCalculateYearlyInsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);

        final BigDecimal result = yearlyInsurancePriceStrategy.calculateInsurance(insurance, new BigDecimal("1.95"), new BigDecimal("20.00"));

        assertEquals(0, result.compareTo(new BigDecimal("78.00")));
    }

    @Test
    public void testCalculateYearlyNoReinsurancesInsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);
        insurance.setReinsurances(null);

        final BigDecimal result = yearlyInsurancePriceStrategy.calculateInsurance(insurance, BigDecimal.ZERO, new BigDecimal("20.00"));

        assertEquals(0, result.compareTo(new BigDecimal("40.00")));
    }

    @Test
    public void testCalculateYearlyNoReinsurancesV2InsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);
        insurance.setReinsurances(null);

        final BigDecimal result = yearlyInsurancePriceStrategy.calculateInsurance(insurance, null, new BigDecimal("20.00"));

        assertEquals(0, result.compareTo(new BigDecimal("40.00")));
    }
}
