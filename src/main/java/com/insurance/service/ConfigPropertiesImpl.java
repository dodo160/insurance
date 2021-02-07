package com.insurance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ConfigPropertiesImpl implements ConfigProperties {

	public static final String PREFIX = "ext.param.";

	@Autowired
	private Environment environment;

	@Override
	public String getConfigValue(final String confKey) {
		return environment.getProperty(confKey);
	}

}
