package com.insurance.json;

import javax.xml.bind.ValidationException;

public interface JsonMarshaller {

    public <T> T fromJson(Class<T> entityClass, String jsonString);

    public <T> String toJson(T entity) throws ValidationException;

    public boolean isValidJson(String jsonString);
}
