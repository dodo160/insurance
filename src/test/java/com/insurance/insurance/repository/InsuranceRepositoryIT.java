package com.insurance.insurance.repository;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.integration.AbstractMySqlIntegrationTest;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InsuranceRepositoryIT extends AbstractMySqlIntegrationTest {

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
        assertNotNull(savedInsurance);
        assertNotNull(savedInsurance.getTariff());
        assertNotNull(savedInsurance.getId());
        assertEquals(insurance.getTariff().getInsuranceType(), savedInsurance.getTariff().getInsuranceType());
        assertEquals(insurance.getTariff().getPacket(), savedInsurance.getTariff().getPacket());
        assertEquals(insurance.getTariff().getPrice().setScale(2, RoundingMode.HALF_UP), savedInsurance.getTariff().getPrice().setScale(2, RoundingMode.HALF_UP));
        assertNotNull(savedInsurance.getUser());
        assertNotNull(savedInsurance.getUser().getId());
        assertNotNull(savedInsurance.getReinsurances());
        assertEquals(2, insurance.getReinsurances().size());
        assertEquals(insurance.getPrice(), savedInsurance.getPrice());
        assertEquals(insurance.getPerson(), savedInsurance.getPerson());
    }
}
