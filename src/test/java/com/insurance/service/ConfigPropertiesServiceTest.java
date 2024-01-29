package com.insurance.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ConfigPropertiesServiceTest {

    @InjectMocks
    private ConfigPropertiesImpl configProperties;

    @Mock
    private Environment environment;

    private static final String STORNO = "ext.param.DAY.STORNO";

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getConfigValueTest(){

        when(environment.getProperty(STORNO)).thenReturn("1.5");

        final String result = configProperties.getConfigValue(STORNO);

        Assert.assertEquals("1.5",result);
    }
}
