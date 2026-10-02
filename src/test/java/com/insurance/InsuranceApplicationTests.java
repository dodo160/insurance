package com.insurance;

import com.insurance.common.xml.xmlvalidator.XmlValidator;
import com.insurance.insurance.config.ReinsuranceConfigProperties;
import com.insurance.insurance.repository.InsuranceRepository;
import com.insurance.insurance.service.InsuranceService;
import com.insurance.tariff.repository.TariffRepository;
import com.insurance.tariff.service.TariffService;
import com.insurance.temporal.repository.TemporalEntityRepository;
import com.insurance.temporal.service.TemporalEntityService;
import com.insurance.user.repository.UserRepository;
import com.insurance.user.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsuranceApplicationTests {

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private TariffRepository tariffRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReinsuranceConfigProperties reinsuranceConfigProperties;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private TariffService tariffService;

    @Autowired
    private UserService userService;

    @Autowired
    private TemporalEntityService temporalEntityService;

    @Autowired
    private TemporalEntityRepository temporalEntityRepository;

    @Autowired
    private XmlValidator xmlValidator;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(insuranceRepository);
        Assert.assertNotNull(tariffRepository);
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(reinsuranceConfigProperties);
        Assert.assertNotNull(insuranceService);
        Assert.assertNotNull(tariffService);
        Assert.assertNotNull(userService);
        Assert.assertNotNull(temporalEntityService);
        Assert.assertNotNull(temporalEntityRepository);
        Assert.assertNotNull(xmlValidator);
    }
}
