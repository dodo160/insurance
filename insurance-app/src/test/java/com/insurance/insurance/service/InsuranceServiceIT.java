package com.insurance.insurance.service;

import com.insurance.common.exception.NotFoundException;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.insurance.repository.InsuranceRepository;
import com.insurance.insurance.service.calculator.InsurancePriceCalculator;
import com.insurance.integration.AbstractMySqlIntegrationTest;
import com.insurance.tariff.enums.Packet;
import com.insurance.tariff.service.TariffService;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.insurance.utils.TestUtils.buildInsurance;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class InsuranceServiceIT extends AbstractMySqlIntegrationTest {

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private InsurancePriceCalculator insurancePriceCalculator;

    @MockBean
    private InsuranceRepository insuranceRepository;

    @MockBean
    private TariffService tariffService;

    @Test
    public void findAllTest() {
        when(insuranceRepository.findAll()).thenReturn(Set.of(new Insurance()));

        final List<Insurance> result = insuranceService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        final Insurance insurance = new Insurance();
        insurance.setId(1l);
        when(insuranceRepository.findById(1l)).thenReturn(Optional.of(insurance));

        final Insurance result = insuranceService.findById(1l);

        assertNotNull(result);
        assertEquals(Long.valueOf(1), result.getId());
    }

    @Test
    public void calculateInsuranceDAYTest() {
        final Insurance insurance = buildInsurance(InsuranceType.DAY);

        when(tariffService.getTariffByInsuranceTypeAndPacketAndActiveTrue(InsuranceType.DAY, Packet.BASIC)).thenReturn(insurance.getTariff());

        final BigDecimal result = insuranceService.calculateInsurance(insurance);

        assertNotNull(result);
        assertEquals(new BigDecimal("14.04"), result);
    }

    @Test
    public void calculateInsuranceNullTest() {
        try {
            insuranceService.calculateInsurance(null);
            fail();
        } catch (ConstraintViolationException e) {
            assertTrue(e.getConstraintViolations().stream()
                    .anyMatch(x -> x.getMessage().equals("Insurance must not be null")));
        }
    }

    @Test
    public void calculateInsuranceDAYNullDBTariffTest() {
        final Insurance insurance = buildInsurance(InsuranceType.DAY);

        when(tariffService.getTariffByInsuranceTypeAndPacketAndActiveTrue(InsuranceType.DAY, Packet.BASIC)).thenReturn(null);

        try {
            insuranceService.calculateInsurance(insurance);
            fail("ValidationException should thrown");
        } catch (Exception e) {
            assertEquals("Tariff doesn't exist", e.getMessage());
        }
    }

    @Test
    public void calculateInsuranceDAYNullTariffTest() {
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.setTariff(null);

        try {
            insuranceService.calculateInsurance(insurance);
            fail("ValidationException should thrown");
        } catch (ConstraintViolationException constraintViolationException) {
            assertTrue(constraintViolationException.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .anyMatch(message -> message.equals("Missing tariff")));
        }
    }

    @Test
    public void calculateInsuranceDAYNullInsuranceTest() {
        try {
            insuranceService.calculateInsurance(null);
            fail("ValidationException should thrown");
        } catch (ConstraintViolationException constraintViolationException) {
            assertTrue(constraintViolationException.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .anyMatch(message -> message.equals("Insurance must not be null")));
        }
    }

    @Test
    public void calculateInsuranceYEARTest() {
        final Insurance insurance = buildInsurance(InsuranceType.YEAR);

        when(tariffService.getTariffByInsuranceTypeAndPacketAndActiveTrue(InsuranceType.YEAR, Packet.BASIC)).thenReturn(insurance.getTariff());

        final BigDecimal result = insuranceService.calculateInsurance(insurance);

        assertNotNull(result);
        assertEquals(new BigDecimal("102.96"), result);
    }

    @Test
    public void addTest() {
        final Insurance insurance = buildInsurance(InsuranceType.DAY);

        when(tariffService.getTariffByInsuranceTypeAndPacketAndActiveTrue(InsuranceType.DAY, Packet.BASIC)).thenReturn(insurance.getTariff());

        assertNull(insurance.getPrice());

        insuranceService.add(insurance);

        verify(insuranceRepository, times(1)).save(insurance);
        assertEquals(new BigDecimal("14.04"), insurance.getPrice());
    }

    @Test
    public void deleteByIdExceptionTest() {
        try {
            insuranceService.deleteById(1L);
        } catch (NotFoundException e) {
            assertEquals("Insurance not found", e.getMessage());
        }
    }

    @Test
    public void deleteByIdTest() {
        when(insuranceRepository.findById(1l)).thenReturn(Optional.of(TestUtils.buildInsurance(InsuranceType.YEAR)));
        try {
            insuranceService.deleteById(1L);
        } catch (NotFoundException e) {
            fail();
        }
        verify(insuranceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void softDeleteByIdTest() {
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        when(insuranceRepository.findById(1L)).thenReturn(Optional.of(insurance));

        insuranceService.softDeleteById(1L);

        assertNotNull(insurance.getDeletedDate());
    }

    @Test()
    public void softDeleteByIdNullInsuranceTest() {
        when(insuranceRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            insuranceService.softDeleteById(1L);
        } catch (NotFoundException e) {
            assertEquals("Insurance not found", e.getMessage());
        }
    }

    @Test
    public void updateTest() {
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.setEndDate(LocalDate.now().plusDays(4));
        insurance.setPerson(3);

        when(tariffService.getTariffByInsuranceTypeAndPacketAndActiveTrue(InsuranceType.DAY, Packet.BASIC)).thenReturn(insurance.getTariff());
        when(insuranceRepository.findById(insurance.getId())).thenReturn(Optional.of(buildInsurance(InsuranceType.DAY)));

        try {
            final Insurance result = insuranceService.update(insurance.getId(), insurance);

            assertNotNull(result);
            assertEquals(new BigDecimal("35.10"), result.getPrice());
            assertEquals(insurance.getPerson(), result.getPerson());
            assertEquals(insurance.getEndDate(), result.getEndDate());
            assertEquals(2, result.getReinsurances().size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void updateInsuranceDoesntExistTest() {
        final Insurance insurance = buildInsurance(InsuranceType.DAY);

        when(insuranceRepository.findById(insurance.getId())).thenReturn(Optional.empty());

        try {
            insuranceService.update(insurance.getId(), insurance);
            fail();
        } catch (Exception e) {
            assertEquals("Insurance not found", e.getMessage());
        }
    }
}
