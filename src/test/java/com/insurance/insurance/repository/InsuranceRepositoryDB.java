package com.insurance.insurance.repository;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.integration.AbstractMySqlIntegrationTest;
import com.insurance.utils.TestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InsuranceRepositoryDB extends AbstractMySqlIntegrationTest {

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Test
    public void saveTest() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        insurance.setId(null);
        insurance.setPrice(BigDecimal.TEN);
        insurance.updateReinsurances();
        insurance.getReinsurances().forEach(x -> x.setId(null));

        final Insurance savedInsurance = insuranceRepository.save(insurance);
        Assert.assertNotNull(savedInsurance);
        Assert.assertNotNull(savedInsurance.getTariff());
        Assert.assertNotNull(savedInsurance.getId());
        Assert.assertEquals(insurance.getTariff().getInsuranceType(), savedInsurance.getTariff().getInsuranceType());
        Assert.assertEquals(insurance.getTariff().getPacket(), savedInsurance.getTariff().getPacket());
        Assert.assertEquals(insurance.getTariff().getPrice().setScale(2, RoundingMode.HALF_UP), savedInsurance.getTariff().getPrice().setScale(2, RoundingMode.HALF_UP));
        Assert.assertNotNull(savedInsurance.getUser());
        Assert.assertNotNull(savedInsurance.getUser().getId());
        Assert.assertNotNull(savedInsurance.getReinsurances());
        Assert.assertEquals(2, insurance.getReinsurances().size());
        Assert.assertEquals(insurance.getPrice(), savedInsurance.getPrice());
        Assert.assertEquals(insurance.getPerson(), savedInsurance.getPerson());
    }
}
