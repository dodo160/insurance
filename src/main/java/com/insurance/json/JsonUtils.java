package com.insurance.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.ValidationException;

public class JsonUtils {

    private JsonUtils() {
    }

    public static Object fromJson(final Class entityClass, final String jsonString) throws ValidationException {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, entityClass);
        } catch (JsonProcessingException e) {
            throw  new ValidationException(e);
        }
    }

    public static String toJson(final Object entity) throws ValidationException {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw  new ValidationException(e);
        }
    }

    public static boolean isValidJson(final String jsonString) {
        try{
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonString);
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
