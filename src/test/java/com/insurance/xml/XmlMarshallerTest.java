package com.insurance.xml;

import com.insurance.TestUtils;
import com.insurance.enums.InsuranceType;
import com.insurance.model.Insurance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.ValidationException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class XmlMarshallerTest {

    @Autowired
    private XmlMarshaller xmlMarshaller;

    @Test
    public void xmlUtilsTest(){
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        try {
            final String xmlString = xmlMarshaller.toXml(Insurance.class, insurance);
            Assert.assertNotNull(xmlString);

            final Insurance insuranceFromXML = (Insurance) xmlMarshaller.fromXml(Insurance.class, xmlString);
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
