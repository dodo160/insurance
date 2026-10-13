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
public class DailyInsurancePriceStrategyTest {

    private DailyInsurancePriceStrategy dailyInsurancePriceStrategy;

    @BeforeEach
    public void setUp() {
        dailyInsurancePriceStrategy = new DailyInsurancePriceStrategy();
    }

    @Test
    public void testCalculateDailyInsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);

        final BigDecimal result = dailyInsurancePriceStrategy.calculateInsurance(insurance, new BigDecimal("1.95"), new BigDecimal("1.5"));

        assertEquals(0, result.compareTo(new BigDecimal("17.55")));
    }

    @Test
    public void testCalculateDailyNoReinsurancesInsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        insurance.setReinsurances(null);

        final BigDecimal result = dailyInsurancePriceStrategy.calculateInsurance(insurance, BigDecimal.ZERO, new BigDecimal("1.5"));

        assertEquals(0, result.compareTo(new BigDecimal("9.00")));
    }

    @Test
    public void testCalculateDailyNoReinsurancesV2InsurancePrice() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        insurance.setReinsurances(null);

        final BigDecimal result = dailyInsurancePriceStrategy.calculateInsurance(insurance, null, new BigDecimal("1.5"));

        assertEquals(0, result.compareTo(new BigDecimal("9.00")));
    }

}
