package com.insurance.validators;

import com.insurance.enums.InsuranceType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.insurance.TestUtils.buildInsurance;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ValidatorInsuranceYearITtest {

    @Autowired
    private InsuranceValidator validatorInsuranceYear;

    @Test
    public void validateTest(){
        validatorInsuranceYear.validate(buildInsurance(InsuranceType.YEAR));
    }
}
