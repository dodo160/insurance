package com.insurance.service;

import com.insurance.TestUtils;
import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.enums.ReinsuranceType;
import com.insurance.model.Insurance;
import com.insurance.model.Reinsurance;
import com.insurance.model.Tariff;
import com.insurance.repository.InsuranceRepository;
import com.insurance.validators.ValidatorFactory;
import com.insurance.validators.ValidatorInsuranceDay;
import com.insurance.validators.ValidatorInsuranceYear;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static com.insurance.TestUtils.buildInsurance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class InsuranceServiceMockTest {

    @InjectMocks
    private InsuranceServiceImpl insuranceService;

    @Mock
    private InsuranceRepository insuranceRepository;

    @Mock
    private ValidatorFactory validatorFactory;

    @Mock
    private TariffService tariffService;

    @Mock
    private ConfigProperties configProperties;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest(){
        when(insuranceRepository.findAll()).thenReturn(Set.of(new Insurance()));

        final Set<Insurance> result =insuranceService.findAll();

        Assert.assertTrue(result.size() == 1);
    }

    @Test
    public void findByIdTest(){
        final Insurance insurance = new Insurance();
        insurance.setId(1l);
        when(insuranceRepository.findById(1l)).thenReturn(Optional.of(insurance));

        final Insurance result = insuranceService.findById(1l);

        Assert.assertNotNull(result);
        Assert.assertEquals(Long.valueOf(1), result.getId());
    }

    @Test
    public void calculateInsuranceDAYTest() throws ValidationException {
        final Insurance insurance = buildInsurance(InsuranceType.DAY);

        when(validatorFactory.getValidator(InsuranceType.DAY)).thenReturn(new ValidatorInsuranceDay());
        when(tariffService.getTariffInsuranceTypeAndPacket(InsuranceType.DAY, Packet.BASIC)).thenReturn(insurance.getTariff());
        when(configProperties.getConfigValue("ext.param.DAY.STORNO")).thenReturn("1.5");
        when(configProperties.getConfigValue("ext.param.DAY.SPORTS_ACTIVITY")).thenReturn("1.3");

        final BigDecimal result = insuranceService.calculateInsurance(insurance);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BigDecimal(14.04).setScale(2, RoundingMode.HALF_UP), result);
    }

    @Test
    public void calculateInsuranceDAYNullDBTariffTest() {
        final Insurance insurance = buildInsurance(InsuranceType.DAY);

        when(validatorFactory.getValidator(InsuranceType.DAY)).thenReturn(new ValidatorInsuranceDay());
        when(tariffService.getTariffInsuranceTypeAndPacket(InsuranceType.DAY, Packet.BASIC)).thenReturn(null);

        try {
            insuranceService.calculateInsurance(insurance);
            Assert.fail("ValidationException should thrown");
        } catch (ValidationException e) {
            Assert.assertTrue("Tarif doesn't exist".equals(e.getMessage()));
        }
    }

    @Test
    public void calculateInsuranceDAYNullTariffTest() {
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.setTariff(null);

        try {
            insuranceService.calculateInsurance(insurance);
            Assert.fail("ValidationException should thrown");
        } catch (Exception e) {
            Assert.assertTrue("Missing Tariff".equals(e.getMessage()));
        }
    }

    @Test
    public void calculateInsuranceDAYNullInsuranceTest() {
        try {
            insuranceService.calculateInsurance(null);
            Assert.fail("ValidationException should thrown");
        } catch (Exception e) {
            Assert.assertTrue("Missing Insurance".equals(e.getMessage()));
        }
    }

    @Test
    public void calculateInsuranceYEARTest() throws ValidationException {
        final Insurance insurance = buildInsurance(InsuranceType.YEAR);

        when(validatorFactory.getValidator(InsuranceType.YEAR)).thenReturn(new ValidatorInsuranceYear());
        when(tariffService.getTariffInsuranceTypeAndPacket(InsuranceType.YEAR, Packet.BASIC)).thenReturn(insurance.getTariff());
        when(configProperties.getConfigValue("ext.param.YEAR.STORNO")).thenReturn("1.2");
        when(configProperties.getConfigValue("ext.param.YEAR.SPORTS_ACTIVITY")).thenReturn("1.1");

        final BigDecimal result = insuranceService.calculateInsurance(insurance);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BigDecimal(102.96).setScale(2, RoundingMode.HALF_UP), result);
    }

    @Test
    public void addTest() throws ValidationException {
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        when(validatorFactory.getValidator(InsuranceType.DAY)).thenReturn(new ValidatorInsuranceDay());
        when(tariffService.getTariffInsuranceTypeAndPacket(InsuranceType.DAY, Packet.BASIC)).thenReturn(insurance.getTariff());
        when(configProperties.getConfigValue("ext.param.DAY.STORNO")).thenReturn("1.5");
        when(configProperties.getConfigValue("ext.param.DAY.SPORTS_ACTIVITY")).thenReturn("1.3");

        Assert.assertNull(insurance.getPrice());

        insuranceService.add(insurance);

        verify(insuranceRepository,times(1)).save(insurance);
        Assert.assertEquals(new BigDecimal(14.04).setScale(2, RoundingMode.HALF_UP),insurance.getPrice());
    }

    @Test
    public void deleteByIdTest(){
        insuranceService.deleteById(1L);
        verify(insuranceRepository,times(1)).deleteById(1L);
    }

    @Test
    public void softDeleteByIdTest(){
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        when(insuranceRepository.findById(1L)).thenReturn(Optional.of(insurance));

        insuranceService.softDeleteById(1L);

        Assert.assertNotNull(insurance.getDeletedDate());
        verify(insuranceRepository, times(1)).save(insurance);
    }

    @Test
    public void softDeleteByIdNullInsuranceTest(){
        when(insuranceRepository.findById(1L)).thenReturn(Optional.empty());

        insuranceService.softDeleteById(1L);

        verify(insuranceRepository, times(0)).save(any());
    }

    @Test
    public void updateTest(){
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.setEndDate(LocalDate.now().plusDays(4));
        insurance.setPerson(3);

        when(validatorFactory.getValidator(InsuranceType.DAY)).thenReturn(new ValidatorInsuranceDay());
        when(tariffService.getTariffInsuranceTypeAndPacket(InsuranceType.DAY, Packet.BASIC)).thenReturn(insurance.getTariff());
        when(configProperties.getConfigValue("ext.param.DAY.STORNO")).thenReturn("1.5");
        when(configProperties.getConfigValue("ext.param.DAY.SPORTS_ACTIVITY")).thenReturn("1.3");
        when(insuranceRepository.findById(insurance.getId())).thenReturn(Optional.of(buildInsurance(InsuranceType.DAY)));

        final ArgumentCaptor<Insurance> insuranceArgumentCaptor = ArgumentCaptor.forClass(Insurance.class);

        try {
            insuranceService.update(insurance);
        } catch (ValidationException e) {
            Assert.fail();
        }

        verify(insuranceRepository,times(1)).save(insuranceArgumentCaptor.capture());
        final Insurance insuranceArgumentCaptorValue = insuranceArgumentCaptor.getValue();
        Assert.assertEquals(new BigDecimal(35.10).setScale(2, RoundingMode.HALF_UP), insuranceArgumentCaptorValue.getPrice());
        Assert.assertEquals(insurance.getPerson(),insuranceArgumentCaptorValue.getPerson());
        Assert.assertEquals(insurance.getEndDate(), insuranceArgumentCaptorValue.getEndDate());
    }

    @Test
    public void updateInsuranceDoesntExistTest(){
        final Insurance insurance = buildInsurance(InsuranceType.DAY);

        when(insuranceRepository.findById(insurance.getId())).thenReturn(Optional.empty());

        try {
            insuranceService.update(insurance);
            Assert.fail();
        } catch (ValidationException e) {
            Assert.assertTrue("Insurance entity doesn't exist".equals(e.getMessage()));
        }
    }
}
