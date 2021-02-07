package com.insurance.validators;

import com.insurance.enums.InsuranceType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ValidatorFactoryITtest {

    @Autowired
    private ValidatorFactory validatorFactory;

    @Test
    public void getValidatorsTest(){
        Assert.assertEquals(3,validatorFactory.getValidators().size());
    }

    @Test
    public void getValidatorDayTest(){
        final InsuranceValidator validator = validatorFactory.getValidator(InsuranceType.DAY);

        Assert.assertNotNull(validator);
        Assert.assertTrue(validator instanceof ValidatorInsuranceDay);
    }

    @Test
    public void getValidatorYearTest(){
        final InsuranceValidator validator = validatorFactory.getValidator(InsuranceType.YEAR);

        Assert.assertNotNull(validator);
        Assert.assertTrue(validator instanceof ValidatorInsuranceYear);
    }

    @Test
    public void getValidatorDummyTest(){
        final InsuranceValidator validator = validatorFactory.getValidator(null);

        Assert.assertNotNull(validator);
        Assert.assertTrue(validator instanceof DummyInsuranceValidator);
    }

}
