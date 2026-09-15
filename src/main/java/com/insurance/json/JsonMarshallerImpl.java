package com.insurance.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.exception.InvalidJsonException;
import com.insurance.exception.JsonSerializationException;
import org.springframework.stereotype.Component;

@Component
public class JsonMarshallerImpl implements JsonMarshaller {

    private final ObjectMapper objectMapper;

    public JsonMarshallerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T fromJson(final Class<T> entityClass, final String jsonString) {
        try {
            return objectMapper.readValue(jsonString, entityClass);
        } catch (JsonProcessingException e) {
            throw new InvalidJsonException("Unable to deserialize JSON to "
                    + entityClass.getSimpleName(), e);
        }
    }

    @Override
    public <T> String toJson(final T entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("Unable to serialize "
                    + entity.getClass().getSimpleName() + " to JSON", e);
        }
    }

    @Override
    public boolean isValidJson(final String jsonString) {
        try {
            objectMapper.readTree(jsonString);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
