package com.insurance.json;

import com.insurance.TestUtils;
import com.insurance.enums.InsuranceType;
import com.insurance.model.Insurance;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.ValidationException;

@SpringBootTest
public class JsonUtilsTest {

    @Test
    public void jsonUtilsTest(){
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        try {
            final String jsonString = JsonUtils.toJson(Insurance.class, insurance);
            Assert.assertNotNull(jsonString);

            final Insurance insuranceFromJson = (Insurance) JsonUtils.fromJson(Insurance.class, jsonString);
            Assert.assertNotNull(insuranceFromJson);
            Assert.assertEquals(insurance.getId(),insuranceFromJson.getId());
            Assert.assertEquals(insurance.getStartDate(), insuranceFromJson.getStartDate());
            Assert.assertEquals(insurance.getEndDate(), insuranceFromJson.getEndDate());
            Assert.assertEquals(insurance.getReinsurances().size(), insuranceFromJson.getReinsurances().size());
            Assert.assertEquals(insurance.getPerson(), insuranceFromJson.getPerson());
            Assert.assertEquals(insurance.getTariff(), insuranceFromJson.getTariff());
            Assert.assertEquals(insurance.getUser(), insuranceFromJson.getUser());
        } catch (ValidationException e) {
            Assert.fail();
        }
    }

    @Test
    public void isValidJsonTest(){
        final boolean result = JsonUtils.isValidJson("dasdaojopjo");
        Assert.assertFalse(result);
    }

    @Test
    public void isInValidJsonTest(){
        final boolean result = JsonUtils.isValidJson("{\"insuranceType\":\"DAY\",\"packet\":\"BASIC\",\"price\":1.20}");
        Assert.assertTrue(result);
    }
}
