package com.insurance.validators;

import com.insurance.enums.InsuranceType;
import com.insurance.model.Insurance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static com.insurance.TestUtils.buildInsurance;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ValidatorInsuranceDayITtest {

    @Autowired
    private InsuranceValidator validatorInsuranceDay;

    @Test
    public void validateTest(){
        validatorInsuranceDay.validate(buildInsurance(InsuranceType.DAY));
    }

    @Test
    public void validateMissingInsuranceTest(){
        try {
            validatorInsuranceDay.validate(null);
            Assert.fail();
        }catch (final IllegalArgumentException iae){
            Assert.assertTrue("Missing Insurance".equals(iae.getMessage()));
        }
    }

    @Test
    public void validateMissingTariffTest(){
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.setTariff(null);
        try {
            validatorInsuranceDay.validate(insurance);
            Assert.fail();
        }catch (final IllegalArgumentException iae){
            Assert.assertTrue("Missing Tariff".equals(iae.getMessage()));
        }
    }

    @Test
    public void validateMissingInsuranceTypeTest(){
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.getTariff().setInsuranceType(null);
        try {
            validatorInsuranceDay.validate(insurance);
            Assert.fail();
        }catch (final IllegalArgumentException iae){
            Assert.assertTrue("Empty InsuranceType".equals(iae.getMessage()));
        }
    }

    @Test
    public void validateMissingPriceTest(){
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.getTariff().setPrice(null);
        try {
            validatorInsuranceDay.validate(insurance);
            Assert.fail();
        }catch (final IllegalArgumentException iae){
            Assert.assertTrue("Missing tariffPrice".equals(iae.getMessage()));
        }
    }

    @Test
    public void validateMissingStartDateTest(){
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.setStartDate(null);
        try {
            validatorInsuranceDay.validate(insurance);
            Assert.fail();
        }catch (final IllegalArgumentException iae){
            Assert.assertTrue("Empty StartDate".equals(iae.getMessage()));
        }
    }

    @Test
    public void validateStartDateBeforeTodayTest(){
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.setStartDate(LocalDate.now().minusDays(1));
        try {
            validatorInsuranceDay.validate(insurance);
            Assert.fail();
        }catch (final IllegalArgumentException iae){
            Assert.assertTrue("Date before today".equals(iae.getMessage()));
        }
    }

    @Test
    public void validateMissingPacketTest(){
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.getTariff().setPacket(null);
        try {
            validatorInsuranceDay.validate(insurance);
            Assert.fail();
        }catch (final IllegalArgumentException iae){
            Assert.assertTrue("Empty Packet".equals(iae.getMessage()));
        }
    }

    @Test
    public void validatePersonOutOfRangeTest(){
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.setPerson(4);
        try {
            validatorInsuranceDay.validate(insurance);
            Assert.fail();
        }catch (final IllegalArgumentException iae){
            Assert.assertTrue("Invalid Person range".equals(iae.getMessage()));
        }
    }


    @Test
    public void validateMissingEndDateTest(){
        final Insurance insurance = buildInsurance(InsuranceType.DAY);
        insurance.setEndDate(LocalDate.now().minusDays(1));
        try {
            validatorInsuranceDay.validate(insurance);
            Assert.fail();
        }catch (final IllegalArgumentException iae){
            Assert.assertTrue("EndDate before StartDate".equals(iae.getMessage()));
        }
    }
}
