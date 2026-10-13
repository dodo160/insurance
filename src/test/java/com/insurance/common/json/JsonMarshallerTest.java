package com.insurance.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.utils.TestUtils;
import org.junit.Test;

import javax.xml.bind.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonMarshallerTest {

    private JsonMarshaller jsonMarshaller = new JsonMarshallerImpl(new ObjectMapper());

    @Test
    public void jsonUtilsTest() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.DAY);
        try {
            final String jsonString = jsonMarshaller.toJson(insurance);
            assertNotNull(jsonString);

            final Insurance insuranceFromJson = jsonMarshaller.fromJson(Insurance.class, jsonString);
            assertNotNull(insuranceFromJson);
            assertEquals(insurance.getId(), insuranceFromJson.getId());
            assertEquals(insurance.getStartDate(), insuranceFromJson.getStartDate());
            assertEquals(insurance.getEndDate(), insuranceFromJson.getEndDate());
            assertEquals(insurance.getReinsurances().size(), insuranceFromJson.getReinsurances().size());
            assertEquals(insurance.getPerson(), insuranceFromJson.getPerson());
            assertEquals(insurance.getTariff(), insuranceFromJson.getTariff());
            assertEquals(insurance.getUser(), insuranceFromJson.getUser());
        } catch (ValidationException e) {
            fail();
        }
    }

    @Test
    public void isValidJsonTest() {
        final boolean result = jsonMarshaller.isValidJson("dasdaojopjo");
        assertFalse(result);
    }

    @Test
    public void isInValidJsonTest() {
        final boolean result = jsonMarshaller.isValidJson("{\"insuranceType\":\"DAY\",\"packet\":\"BASIC\",\"price\":1.20}");
        assertTrue(result);
    }
}
