package com.insurance.json;

import javax.xml.bind.ValidationException;

public interface JsonMarshaller {

    public  Object fromJson(Class entityClass, String jsonString) throws ValidationException;

    public  String toJson(Object entity) throws ValidationException;

    public boolean isValidJson(String jsonString);
}
