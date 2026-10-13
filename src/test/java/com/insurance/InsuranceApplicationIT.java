package com.insurance;

import com.insurance.common.json.JsonMarshaller;
import com.insurance.common.xml.xmlvalidator.XmlValidator;
import com.insurance.insurance.config.ReinsuranceConfigProperties;
import com.insurance.insurance.repository.InsuranceRepository;
import com.insurance.insurance.service.InsuranceService;
import com.insurance.insurance.service.calculator.InsurancePriceCalculator;
import com.insurance.insurance.service.calculator.ReinsuranceCalculator;
import com.insurance.insurance.service.strategy.DailyInsurancePriceStrategy;
import com.insurance.insurance.service.strategy.YearlyInsurancePriceStrategy;
import com.insurance.integration.AbstractMySqlIntegrationTest;
import com.insurance.tariff.repository.TariffRepository;
import com.insurance.tariff.service.TariffService;
import com.insurance.temporal.repository.TemporalEntityRepository;
import com.insurance.temporal.service.TemporalEntityService;
import com.insurance.user.repository.UserRepository;
import com.insurance.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class InsuranceApplicationIT extends AbstractMySqlIntegrationTest {

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

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Autowired
    private DailyInsurancePriceStrategy dailyInsurancePriceStrategy;

    @Autowired
    private YearlyInsurancePriceStrategy yearlyInsurancePriceStrategy;

    @Autowired
    private InsurancePriceCalculator insurancePriceCalculator;

    @Autowired
    private ReinsuranceCalculator reinsuranceCalculator;

    @Test
    public void contextLoads() {
        assertNotNull(insuranceRepository);
        assertNotNull(tariffRepository);
        assertNotNull(userRepository);
        assertNotNull(reinsuranceConfigProperties);
        assertNotNull(insuranceService);
        assertNotNull(tariffService);
        assertNotNull(userService);
        assertNotNull(temporalEntityService);
        assertNotNull(temporalEntityRepository);
        assertNotNull(xmlValidator);
        assertNotNull(jsonMarshaller);
        assertNotNull(dailyInsurancePriceStrategy);
        assertNotNull(yearlyInsurancePriceStrategy);
        assertNotNull(insurancePriceCalculator);
        assertNotNull(reinsuranceCalculator);
    }
}
