package com.insurance.insurance.service.calculator;

import com.insurance.TestUtils;
import com.insurance.insurance.config.ReinsuranceConfigProperties;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.enums.ReinsuranceType;
import com.insurance.insurance.model.Reinsurance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReinsuranceCalculatorTest {

    private ReinsuranceCalculator reinsuranceCalculator;
    @Mock
    private ReinsuranceConfigProperties reinsuranceConfigProperties;

    @Before
    public void setUp() {
        reinsuranceCalculator = new ReinsuranceCalculator(reinsuranceConfigProperties);
    }

    @Test
    public void shouldCalculateYearStornoAndSportsActivity() {
        final Reinsurance reinsuranceStorno = TestUtils.buildReinsurance(ReinsuranceType.STORNO);
        final Reinsurance reinsuranceSports = TestUtils.buildReinsurance(ReinsuranceType.SPORTS_ACTIVITY);

        when(reinsuranceConfigProperties.get(InsuranceType.YEAR, ReinsuranceType.STORNO)).thenReturn(new BigDecimal("1.20"));
        when(reinsuranceConfigProperties.get(InsuranceType.YEAR, ReinsuranceType.SPORTS_ACTIVITY)).thenReturn(new BigDecimal("1.10"));

        final BigDecimal result = reinsuranceCalculator.calculateReinsurance(Set.of(reinsuranceStorno, reinsuranceSports), InsuranceType.YEAR);

        Assert.assertEquals(0, new BigDecimal("1.32").compareTo(result));
    }

    @Test
    public void shouldCalculateYearStorno() {
        final Reinsurance reinsuranceStorno = TestUtils.buildReinsurance(ReinsuranceType.STORNO);

        when(reinsuranceConfigProperties.get(InsuranceType.YEAR, ReinsuranceType.STORNO)).thenReturn(new BigDecimal("1.20"));

        final BigDecimal result = reinsuranceCalculator.calculateReinsurance(Set.of(reinsuranceStorno), InsuranceType.YEAR);

        Assert.assertEquals(0, new BigDecimal("1.20").compareTo(result));
    }

    @Test
    public void shouldCalculateDayStorno() {
        final Reinsurance reinsuranceStorno = TestUtils.buildReinsurance(ReinsuranceType.STORNO);

        when(reinsuranceConfigProperties.get(InsuranceType.DAY, ReinsuranceType.STORNO)).thenReturn(new BigDecimal("1.50"));

        final BigDecimal result = reinsuranceCalculator.calculateReinsurance(Set.of(reinsuranceStorno), InsuranceType.DAY);

        Assert.assertEquals(0, new BigDecimal("1.50").compareTo(result));
    }

    @Test
    public void shouldCalculateDayStornoAndSportsActivity() {
        final Reinsurance reinsuranceStorno = TestUtils.buildReinsurance(ReinsuranceType.STORNO);
        final Reinsurance reinsuranceSports = TestUtils.buildReinsurance(ReinsuranceType.SPORTS_ACTIVITY);

        when(reinsuranceConfigProperties.get(InsuranceType.DAY, ReinsuranceType.STORNO)).thenReturn(new BigDecimal("1.50"));
        when(reinsuranceConfigProperties.get(InsuranceType.DAY, ReinsuranceType.SPORTS_ACTIVITY)).thenReturn(new BigDecimal("1.30"));

        final BigDecimal result = reinsuranceCalculator.calculateReinsurance(Set.of(reinsuranceStorno, reinsuranceSports), InsuranceType.DAY);

        Assert.assertEquals(0, new BigDecimal("1.95").compareTo(result));
    }

    @Test
    public void shouldCalculateDayStornoMissingProperty() {
        final Reinsurance reinsuranceStorno = TestUtils.buildReinsurance(ReinsuranceType.STORNO);

        when(reinsuranceConfigProperties.get(InsuranceType.DAY, ReinsuranceType.STORNO)).thenReturn(null);

        final BigDecimal result = reinsuranceCalculator.calculateReinsurance(Set.of(reinsuranceStorno), InsuranceType.DAY);

        Assert.assertEquals(0, new BigDecimal("1").compareTo(result));
    }
}
