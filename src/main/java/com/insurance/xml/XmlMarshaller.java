package com.insurance.xml;

import com.insurance.model.BaseEntity;

import javax.xml.bind.ValidationException;

public interface XmlMarshaller {

    public Object fromXml(Class entityClass, String xmlString) throws ValidationException;

    public String toXml(Class entityClass, BaseEntity entity) throws ValidationException;
}
