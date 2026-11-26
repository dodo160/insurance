package com.insurance.insurance.service.calculator;

import com.insurance.common.exception.NotFoundException;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.insurance.service.strategy.DailyInsurancePriceStrategy;
import com.insurance.insurance.service.strategy.YearlyInsurancePriceStrategy;
import com.insurance.tariff.client.TariffGateway;
import com.insurance.tariff.model.TariffEmbedded;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InsurancePriceCalculatorTest {

    private InsurancePriceCalculator insurancePriceCalculator;
    private YearlyInsurancePriceStrategy yearlyInsurancePriceStrategy;
    private DailyInsurancePriceStrategy dailyInsurancePriceStrategy;
    @Mock
    private ReinsuranceCalculator reinsuranceCalculator;
    @Mock
    private TariffGateway tariffGateway;

    @BeforeEach
    public void setUp() {
        yearlyInsurancePriceStrategy = new YearlyInsurancePriceStrategy();
        dailyInsurancePriceStrategy = new DailyInsurancePriceStrategy();
        insurancePriceCalculator = new InsurancePriceCalculator(reinsuranceCalculator, tariffGateway, Set.of(dailyInsurancePriceStrategy, yearlyInsurancePriceStrategy));
    }

    @Test
    public void shouldCalculateInsuranceYearTest() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);
        final TariffEmbedded tariff = TestUtils.buildTariff(InsuranceType.YEAR);
        final BigDecimal reinsurancePrice = new BigDecimal("2");

        when(tariffGateway.getTariffByInsuranceTypeAndPacketAndActiveTrue(
                insurance.getTariff().getInsuranceType(),
                insurance.getTariff().getPacket()))
                .thenReturn(tariff);

        when(reinsuranceCalculator.calculateReinsurance(
                insurance.getReinsurances(),
                insurance.getTariff().getInsuranceType()))
                .thenReturn(reinsurancePrice);

        final BigDecimal result = insurancePriceCalculator.calculateInsurance(insurance);

        assertNotNull(result);
        assertEquals(0, new BigDecimal("156.00").compareTo(result));

        verify(tariffGateway).getTariffByInsuranceTypeAndPacketAndActiveTrue(
                InsuranceType.YEAR,
                insurance.getTariff().getPacket());

        verify(reinsuranceCalculator).calculateReinsurance(
                insurance.getReinsurances(),
                InsuranceType.YEAR);
    }

    @Test
    public void shouldCalculateYearlyInsuranceWithoutReinsurance() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);
        final TariffEmbedded tariff = TestUtils.buildTariff(InsuranceType.YEAR);
        final BigDecimal reinsurancePrice = new BigDecimal("1.50");

        when(tariffGateway.getTariffByInsuranceTypeAndPacketAndActiveTrue(
                insurance.getTariff().getInsuranceType(),
                insurance.getTariff().getPacket()))
                .thenReturn(tariff);

        when(reinsuranceCalculator.calculateReinsurance(
                insurance.getReinsurances(),
                insurance.getTariff().getInsuranceType()))
                .thenReturn(reinsurancePrice);

        final BigDecimal result = insurancePriceCalculator.calculateInsurance(insurance);

        assertNotNull(result);
        assertEquals(0, new BigDecimal("117.00").compareTo(result));

        verify(tariffGateway).getTariffByInsuranceTypeAndPacketAndActiveTrue(
                InsuranceType.YEAR,
                insurance.getTariff().getPacket());

        verify(reinsuranceCalculator).calculateReinsurance(
                insurance.getReinsurances(),
                InsuranceType.YEAR);
    }

    @Test
    public void shouldCalculateYearlyInsuranceWithReinsurance() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);
        final TariffEmbedded tariff = TestUtils.buildTariff(InsuranceType.YEAR);
        final BigDecimal reinsurancePrice = new BigDecimal("1.20");

        when(tariffGateway.getTariffByInsuranceTypeAndPacketAndActiveTrue(
                insurance.getTariff().getInsuranceType(),
                insurance.getTariff().getPacket()))
                .thenReturn(tariff);

        when(reinsuranceCalculator.calculateReinsurance(
                insurance.getReinsurances(),
                InsuranceType.YEAR))
                .thenReturn(reinsurancePrice);

        final BigDecimal result = insurancePriceCalculator.calculateInsurance(insurance);

        assertNotNull(result);
        assertEquals(0, new BigDecimal("93.60").compareTo(result));

        verify(tariffGateway).getTariffByInsuranceTypeAndPacketAndActiveTrue(
                InsuranceType.YEAR,
                insurance.getTariff().getPacket());

        verify(reinsuranceCalculator).calculateReinsurance(
                insurance.getReinsurances(),
                InsuranceType.YEAR);
    }

    @Test
    public void shouldCalculateDailyInsurance() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        final TariffEmbedded tariff = TestUtils.buildTariff(InsuranceType.DAY);
        final BigDecimal reinsurancePrice = new BigDecimal("1.80");

        when(tariffGateway.getTariffByInsuranceTypeAndPacketAndActiveTrue(
                insurance.getTariff().getInsuranceType(),
                insurance.getTariff().getPacket()))
                .thenReturn(tariff);

        when(reinsuranceCalculator.calculateReinsurance(
                insurance.getReinsurances(),
                InsuranceType.DAY))
                .thenReturn(reinsurancePrice);

        final BigDecimal result = insurancePriceCalculator.calculateInsurance(insurance);

        assertNotNull(result);
        assertEquals(0, new BigDecimal("12.96").compareTo(result));

        verify(tariffGateway).getTariffByInsuranceTypeAndPacketAndActiveTrue(
                InsuranceType.DAY,
                insurance.getTariff().getPacket());

        verify(reinsuranceCalculator).calculateReinsurance(
                insurance.getReinsurances(),
                InsuranceType.DAY);
    }

    @Test
    public void shouldCalculateDailyInsuranceWithReinsurance() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        final TariffEmbedded tariff = TestUtils.buildTariff(InsuranceType.DAY);
        final BigDecimal reinsurancePrice = new BigDecimal("1.30");

        when(tariffGateway.getTariffByInsuranceTypeAndPacketAndActiveTrue(
                InsuranceType.DAY,
                insurance.getTariff().getPacket()))
                .thenReturn(tariff);

        when(reinsuranceCalculator.calculateReinsurance(
                insurance.getReinsurances(),
                InsuranceType.DAY))
                .thenReturn(reinsurancePrice);

        final BigDecimal result = insurancePriceCalculator.calculateInsurance(insurance);

        assertNotNull(result);
        assertEquals(0, new BigDecimal("9.36").compareTo(result));

        verify(tariffGateway).getTariffByInsuranceTypeAndPacketAndActiveTrue(
                InsuranceType.DAY,
                insurance.getTariff().getPacket());

        verify(reinsuranceCalculator).calculateReinsurance(
                insurance.getReinsurances(),
                InsuranceType.DAY);
    }

    @Test()
    public void shouldThrowNotFoundExceptionWhenTariffDoesNotExist() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);

        when(tariffGateway.getTariffByInsuranceTypeAndPacketAndActiveTrue(
                insurance.getTariff().getInsuranceType(),
                insurance.getTariff().getPacket()))
                .thenReturn(null);

        try {
            insurancePriceCalculator.calculateInsurance(insurance);
        }catch (NotFoundException e){
            assertEquals("Tariff doesn't exist", e.getMessage());
        }

    }
}
