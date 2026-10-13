package com.insurance.insurance.config;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.enums.ReinsuranceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(ReinsuranceConfigProperties.class)
@TestPropertySource(locations = "classpath:reinsurance.properties")
public class ReinsuranceConfigPropertiesTest {

    @Autowired
    private ReinsuranceConfigProperties reinsuranceConfigProperties;

    @Test
    public void configPropertiesTest() {
        assertNotNull(reinsuranceConfigProperties);
        final BigDecimal dayStorno = reinsuranceConfigProperties.get(InsuranceType.DAY, ReinsuranceType.STORNO);
        assertNotNull(dayStorno);
        assertEquals(new BigDecimal("1.5"), dayStorno);
        final BigDecimal daySportsActivity = reinsuranceConfigProperties.get(InsuranceType.DAY, ReinsuranceType.SPORTS_ACTIVITY);
        assertNotNull(daySportsActivity);
        assertEquals(new BigDecimal("1.3"), daySportsActivity);
        final BigDecimal yearStorno = reinsuranceConfigProperties.get(InsuranceType.YEAR, ReinsuranceType.STORNO);
        assertNotNull(yearStorno);
        assertEquals(new BigDecimal("1.2"), yearStorno);
        final BigDecimal yearSportActivity = reinsuranceConfigProperties.get(InsuranceType.YEAR, ReinsuranceType.SPORTS_ACTIVITY);
        assertNotNull(yearSportActivity);
        assertEquals(new BigDecimal("1.1"), yearSportActivity);
    }
}
