package com.insurance;

import com.insurance.repository.InsuranceRepository;
import com.insurance.repository.TariffRepository;
import com.insurance.repository.UserRepository;
import com.insurance.service.ConfigProperties;
import com.insurance.service.InsuranceService;
import com.insurance.service.TariffService;
import com.insurance.service.UserService;
import com.insurance.validators.ValidatorFactory;
import com.insurance.validators.ValidatorInsuranceDay;
import com.insurance.validators.ValidatorInsuranceYear;
import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
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
	private ConfigProperties configProperties;

	@Autowired
	private InsuranceService insuranceService;

	@Autowired
	private TariffService tariffService;

	@Autowired
	private UserService userService;

	@Autowired
	private ValidatorFactory validatorFactory;

	@Autowired
	private ValidatorInsuranceDay validatorInsuranceDay;

	@Autowired
	private ValidatorInsuranceYear validatorInsuranceYear;

	@Test
	public void contextLoads() {
		Assert.assertNotNull(insuranceRepository);
		Assert.assertNotNull(tariffRepository);
		Assert.assertNotNull(userRepository);
		Assert.assertNotNull(configProperties);
		Assert.assertNotNull(insuranceService);
		Assert.assertNotNull(tariffService);
		Assert.assertNotNull(userService);
		Assert.assertNotNull(validatorFactory);
		Assert.assertNotNull(validatorInsuranceDay);
		Assert.assertNotNull(validatorInsuranceYear);
	}

	@Test
	public void configPropertiesTest() {
		Assert.assertNotNull(configProperties);
		final String dayStorno = configProperties.getConfigValue("ext.param.DAY.STORNO");
		Assert.assertTrue(StringUtils.isNotBlank(dayStorno));
		Assert.assertEquals("1.5", dayStorno);
		final String daySportsActivity = configProperties.getConfigValue("ext.param.DAY.SPORTS_ACTIVITY");
		Assert.assertTrue(StringUtils.isNotBlank(daySportsActivity));
		Assert.assertEquals("1.3", daySportsActivity);
		final String yearStorno = configProperties.getConfigValue("ext.param.YEAR.STORNO");
		Assert.assertTrue(StringUtils.isNotBlank(yearStorno));
		Assert.assertEquals("1.2", yearStorno);
		final String yearSportActivity = configProperties.getConfigValue("ext.param.YEAR.SPORTS_ACTIVITY");
		Assert.assertTrue(StringUtils.isNotBlank(yearSportActivity));
		Assert.assertEquals("1.1", yearSportActivity);
	}
}
