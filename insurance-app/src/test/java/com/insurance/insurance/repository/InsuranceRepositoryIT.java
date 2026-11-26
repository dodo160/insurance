package com.insurance.insurance.repository;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.integration.AbstractMySqlIntegrationTest;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InsuranceRepositoryIT extends AbstractMySqlIntegrationTest {

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void saveTest() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        insurance.setId(null);
        insurance.setPrice(BigDecimal.TEN);
        insurance.updateReinsurances();
        insurance.getReinsurances().forEach(x -> x.setId(null));

        final Insurance savedInsurance = insuranceRepository.save(insurance);
        entityManager.flush();
        assertNotNull(savedInsurance);
        assertEquals(insurance.getTariff(), savedInsurance.getTariff());
        assertNotNull(savedInsurance.getId());
        assertNotNull(savedInsurance.getUser());
        assertNotNull(savedInsurance.getUser().getId());
        assertNotNull(savedInsurance.getReinsurances());
        assertEquals(2, insurance.getReinsurances().size());
        assertEquals(insurance.getPrice(), savedInsurance.getPrice());
        assertEquals(insurance.getPerson(), savedInsurance.getPerson());

        entityManager.clear();
        final Insurance reloadedInsurance = insuranceRepository.findById(savedInsurance.getId()).orElseThrow();
        assertEquals(savedInsurance.getTariff(), reloadedInsurance.getTariff());
        assertEquals(0, savedInsurance.getPrice().compareTo(reloadedInsurance.getPrice()));

        reloadedInsurance.setDeletedDate(LocalDateTime.now());
        entityManager.flush();
    }
}
