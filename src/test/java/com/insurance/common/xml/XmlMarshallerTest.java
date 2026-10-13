package com.insurance.common.xml;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XmlMarshallerTest {

    private XmlMarshaller xmlMarshaller = new XmlMarshallerImpl();

    @Test
    public void xmlUtilsTest() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        try {
            final String xmlString = xmlMarshaller.toXml(Insurance.class, insurance);
            assertNotNull(xmlString);

            final Insurance insuranceFromXML = xmlMarshaller.fromXml(Insurance.class, xmlString);
            assertNotNull(insuranceFromXML);
            assertEquals(insurance.getId(), insuranceFromXML.getId());
            assertEquals(insurance.getStartDate(), insuranceFromXML.getStartDate());
            assertEquals(insurance.getEndDate(), insuranceFromXML.getEndDate());
            assertEquals(insurance.getReinsurances().size(), insuranceFromXML.getReinsurances().size());
            assertEquals(insurance.getPerson(), insuranceFromXML.getPerson());
            assertEquals(insurance.getTariff(), insuranceFromXML.getTariff());
            assertEquals(insurance.getUser(), insuranceFromXML.getUser());
        } catch (RuntimeException e) {
            fail();
        }
    }
}
