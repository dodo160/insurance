package com.insurance.repository;

import com.insurance.TestUtils;
import com.insurance.enums.InsuranceType;
import com.insurance.model.Insurance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InsuranceRepositoryTestDB {

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Test
    public void saveTest(){
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        insurance.setPrice(BigDecimal.TEN);
        insurance.updateReinsurances();

        final Insurance savedInsurance = insuranceRepository.save(insurance);
        Assert.assertNotNull(savedInsurance);
        Assert.assertNotNull(savedInsurance.getTariff());
        Assert.assertNotNull(savedInsurance.getId());
        Assert.assertEquals(insurance.getTariff().getInsuranceType(), savedInsurance.getTariff().getInsuranceType());
        Assert.assertEquals(insurance.getTariff().getPacket(), savedInsurance.getTariff().getPacket());
        Assert.assertEquals(insurance.getTariff().getPrice().setScale(2, RoundingMode.HALF_UP), savedInsurance.getTariff().getPrice());
        Assert.assertNotNull(savedInsurance.getUser());
        Assert.assertNotNull(savedInsurance.getUser().getId());
        Assert.assertNotNull(savedInsurance.getReinsurances());
        Assert.assertTrue(insurance.getReinsurances().size() == 2);
        Assert.assertEquals(insurance.getPrice(), savedInsurance.getPrice());
        Assert.assertEquals(insurance.getPerson(), savedInsurance.getPerson());
    }
}
