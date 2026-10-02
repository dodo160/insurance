package com.insurance.insurance.config;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.enums.ReinsuranceType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReinsuranceConfigPropertiesIT {

    @Autowired
    private ReinsuranceConfigProperties reinsuranceConfigProperties;

    @Test
    public void configPropertiesTest() {
        Assert.assertNotNull(reinsuranceConfigProperties);
        final BigDecimal dayStorno = reinsuranceConfigProperties.get(InsuranceType.DAY, ReinsuranceType.STORNO);
        Assert.assertNotNull(dayStorno);
        Assert.assertEquals(new BigDecimal("1.5"), dayStorno);
        final BigDecimal daySportsActivity = reinsuranceConfigProperties.get(InsuranceType.DAY, ReinsuranceType.SPORTS_ACTIVITY);
        Assert.assertNotNull(daySportsActivity);
        Assert.assertEquals(new BigDecimal("1.3"), daySportsActivity);
        final BigDecimal yearStorno = reinsuranceConfigProperties.get(InsuranceType.YEAR, ReinsuranceType.STORNO);
        Assert.assertNotNull(yearStorno);
        Assert.assertEquals(new BigDecimal("1.2"), yearStorno);
        final BigDecimal yearSportActivity = reinsuranceConfigProperties.get(InsuranceType.YEAR, ReinsuranceType.SPORTS_ACTIVITY);
        Assert.assertNotNull(yearSportActivity);
        Assert.assertEquals(new BigDecimal("1.1"), yearSportActivity);
    }
}
