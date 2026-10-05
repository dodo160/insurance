package com.insurance.insurance.service.strategy;

import com.insurance.TestUtils;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class YearlyInsurancePriceStrategyTest {

    private YearlyInsurancePriceStrategy yearlyInsurancePriceStrategy;

    @Before
    public void setUp() {
        yearlyInsurancePriceStrategy = new YearlyInsurancePriceStrategy();
    }

    @Test
    public void testCalculateYearlyInsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);

        final BigDecimal result = yearlyInsurancePriceStrategy.calculateInsurance(insurance, new BigDecimal("1.95"), new BigDecimal("20.00"));

        Assert.assertEquals(0, result.compareTo(new BigDecimal("78.00")));
    }

    @Test
    public void testCalculateYearlyNoReinsurancesInsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);
        insurance.setReinsurances(null);

        final BigDecimal result = yearlyInsurancePriceStrategy.calculateInsurance(insurance, BigDecimal.ZERO, new BigDecimal("20.00"));

        Assert.assertEquals(0, result.compareTo(new BigDecimal("40.00")));
    }

    @Test
    public void testCalculateYearlyNoReinsurancesV2InsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);
        insurance.setReinsurances(null);

        final BigDecimal result = yearlyInsurancePriceStrategy.calculateInsurance(insurance, null, new BigDecimal("20.00"));

        Assert.assertEquals(0, result.compareTo(new BigDecimal("40.00")));
    }
}
