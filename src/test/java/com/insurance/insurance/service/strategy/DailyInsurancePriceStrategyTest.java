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
public class DailyInsurancePriceStrategyTest {

    private DailyInsurancePriceStrategy dailyInsurancePriceStrategy;

    @Before
    public void setUp() {
        dailyInsurancePriceStrategy = new DailyInsurancePriceStrategy();
    }

    @Test
    public void testCalculateDailyInsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);

        final BigDecimal result = dailyInsurancePriceStrategy.calculateInsurance(insurance, new BigDecimal("1.95"), new BigDecimal("1.5"));

        Assert.assertEquals(0, result.compareTo(new BigDecimal("17.55")));
    }

    @Test
    public void testCalculateDailyNoReinsurancesInsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        insurance.setReinsurances(null);

        final BigDecimal result = dailyInsurancePriceStrategy.calculateInsurance(insurance, BigDecimal.ZERO, new BigDecimal("1.5"));

        Assert.assertEquals(0, result.compareTo(new BigDecimal("9.00")));
    }

    @Test
    public void testCalculateDailyNoReinsurancesV2InsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        insurance.setReinsurances(null);

        final BigDecimal result = dailyInsurancePriceStrategy.calculateInsurance(insurance, null, new BigDecimal("1.5"));

        Assert.assertEquals(0, result.compareTo(new BigDecimal("9.00")));
    }

}
