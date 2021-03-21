package com.insurance.xml;

import com.insurance.TestUtils;
import com.insurance.enums.InsuranceType;
import com.insurance.model.Insurance;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.ValidationException;

@SpringBootTest
public class XmlUtilsTest {

    @Test
    public void xmlUtilsTest(){
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        try {
            final String xmlString = XmlUtils.toXml(Insurance.class, insurance);
            Assert.assertNotNull(xmlString);

            final Insurance insuranceFromXML = (Insurance) XmlUtils.fromXml(Insurance.class, xmlString);
            Assert.assertNotNull(insuranceFromXML);
            Assert.assertEquals(insurance.getId(),insuranceFromXML.getId());
            Assert.assertEquals(insurance.getStartDate(), insuranceFromXML.getStartDate());
            Assert.assertEquals(insurance.getEndDate(), insuranceFromXML.getEndDate());
            Assert.assertEquals(insurance.getReinsurances().size(), insuranceFromXML.getReinsurances().size());
            Assert.assertEquals(insurance.getPerson(), insuranceFromXML.getPerson());
            Assert.assertEquals(insurance.getTariff(), insuranceFromXML.getTariff());
            Assert.assertEquals(insurance.getUser(), insuranceFromXML.getUser());
        } catch (ValidationException e) {
            Assert.fail();
        }
    }
}
